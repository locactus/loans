package xyz.platform56.loans.service;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import xyz.platform56.loans.component.DateComponent;
import xyz.platform56.loans.entity.LoanEntity;
import xyz.platform56.loans.entity.LoanPaymentEntity;
import xyz.platform56.loans.entity.ScheduleEntity;
import xyz.platform56.loans.entity.SingleLineScheduleEntity;
import xyz.platform56.loans.exception.ApiException;
import xyz.platform56.loans.exception.NotFoundException;
import xyz.platform56.loans.pojo.*;

import xyz.platform56.loans.repository.LoanPaymentRepository;
import xyz.platform56.loans.repository.LoanRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import xyz.platform56.loans.repository.ScheduleRepository;
import xyz.platform56.loans.repository.SingleLineScheduleRepository;
import xyz.platform56.loans.utils.ModelMapperBasedTransformer;

import java.lang.reflect.Type;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Service
@Slf4j
public class LoanServiceImpl extends AbstractService implements LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private LoanPaymentRepository loanPaymentRepository;

    @Autowired
    private SingleLineScheduleRepository singleLineScheduleRepository;


    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private DateComponent dateComponent;


    @Autowired
    @Qualifier("loanEntityToLoanDetailsModelMapperBasedTransformer")
    private ModelMapperBasedTransformer<LoanEntity, LoanDetailsResponse> loanEntityToLoanDetailsModelMapperBasedTransformer;


    private static final String NOT_FOUND_ERROR_STATUS = "Not Found";
    private static final String NOT_FOUND_ERROR_MESSAGE = "No LoanDetailsRequest Found";


    @Override
    public SearchResponse search(String loanRef, String status, PaginationSearchRequest searchRequest) {
        SearchResponse<LoanEntity> searchResponse = loanRepository.search(loanRef, status, searchRequest);
        return searchResponseConverter.buildSearchResponse(searchResponse, loanEntityToLoanDetailsModelMapperBasedTransformer);
    }

    @Override
    public LoanDetailsResponse get(Long entityId) {
        LoanEntity loanEntity = loanRepository.findOne(entityId);
        checkEntity(loanEntity);
        return modelMapper.map(loanEntity, LoanDetailsResponse.class);
    }

    @Override
    public LoanDetailsResponse create(LoanDetailsRequest request) {
        LoanEntity loanEntity = modelMapper.map(request, LoanEntity.class);
        try {

            loanEntity = loanRepository.save(loanEntity);

        } catch (org.springframework.dao.DataIntegrityViolationException ex) {
            if (ex.getMostSpecificCause().toString().contains("ConstraintViolation")) {
                throw new ApiException("400", ex.getMostSpecificCause().toString());
            } else {
                throw new ApiException("400", "Invalid Data");
            }
        }
        return modelMapper.map(loanEntity, LoanDetailsResponse.class);
    }

    @Override
    public LoanDetailsResponse update(Long entityId, LoanDetailsRequest request) {
        LoanEntity loanEntity = loanRepository.findOne(entityId);
        checkEntity(loanEntity);
        loanEntity = modelMapper.map(request, LoanEntity.class);
        ;
        loanEntity = loanRepository.save(loanEntity);
        return modelMapper.map(loanEntity, LoanDetailsResponse.class);
    }

    @Override
    public ScheduleResponse createSchedule(Long loanId, ScheduleRequest request) {
        ScheduleResponse previewSchedule = this.previewSchedule(request);
        ScheduleEntity scheduleEntity = null;
        scheduleEntity = modelMapper.map(previewSchedule, ScheduleEntity.class);
        scheduleEntity = scheduleRepository.save(scheduleEntity);

        List<SingleLineScheduleEntity> lineScheduleEntities = Lists.newArrayList();
        for (LineSchedule lineSchedule : previewSchedule.getSchedules()) {
            SingleLineScheduleEntity singleLineScheduleEntity = modelMapper.map(lineSchedule, SingleLineScheduleEntity.class);
            singleLineScheduleEntity.setSchedule(scheduleEntity);
            lineScheduleEntities.add(singleLineScheduleEntity);
        }
        Iterable<SingleLineScheduleEntity> savedLineSchedules = singleLineScheduleRepository.save(lineScheduleEntities);
        lineScheduleEntities.clear();
        for (SingleLineScheduleEntity singleLineScheduleEntity : savedLineSchedules) {
            lineScheduleEntities.add(singleLineScheduleEntity);
        }
        scheduleEntity.setSingleLineSchedules(lineScheduleEntities);
        scheduleEntity = scheduleRepository.save(scheduleEntity);

        return modelMapper.map(scheduleEntity, ScheduleResponse.class);
    }

    @Override
    public ScheduleResponse previewSchedule(ScheduleRequest request) {
        LocalDate startDate = request.getStartDate() != null && StringUtils.isNotEmpty(request.getStartDate().toString()) ?
                request.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : LocalDate.now();
        List<DayOfWeek> dayOfWeeks = Lists.newArrayList();

        if (!request.getIncludeSat()) {
            dayOfWeeks.add(DayOfWeek.SATURDAY);
        }
        if (!request.getIncludeSun()) {
            dayOfWeeks.add(DayOfWeek.SUNDAY);
        }

        List<LocalDate> holidayDates = Lists.newArrayList();
        List<LineSchedule> lineSchedules = Lists.newArrayList();

        List<LocalDate> localDates = dateComponent.generateScheduleDate(startDate, request.getNumberOfDays(),
                dayOfWeeks, holidayDates);

        // Computation of InterestRate
        double interestAmount = request.getPrincipal() * (request.getInterestRate() / 100);
        double theNewPrincipal = request.getPrincipal() + interestAmount;
        double perDayAmount = theNewPrincipal / request.getNumberOfDays();

        for (LocalDate localDate : localDates) {
            lineSchedules.add(LineSchedule.builder()
                    .amount(perDayAmount)
                    .status("UNPAID")
                    .paymentDate(localDate).build());
        }

        return ScheduleResponse.builder().
                includeHoliday(request.getIncludeHoliday()).
                includeSat(request.getIncludeSat()).
                includeSun(request.getIncludeSun()).
                startDate(startDate).
                principal(request.getPrincipal()).
                interestRate(request.getInterestRate()).
                numberOfDays(request.getNumberOfDays()).
                totalAmount(theNewPrincipal).
                schedules(lineSchedules)
                .build();
    }


    @Override
    public ScheduleResponse fetchSchedule(Long loanId) {
        ScheduleEntity scheduleEntity = scheduleRepository.findOne(loanId);
        return modelMapper.map(scheduleEntity, ScheduleResponse.class);
    }

    @Override
    public PaymentResponse payLoan(Long loanId, PaymentRequest request) {
        LoanEntity loanEntity = loanRepository.findOne(loanId);
        LoanPaymentEntity loanPaymentEntity = null;
        try {
            loanPaymentEntity = modelMapper.map(request, LoanPaymentEntity.class);
            loanPaymentEntity.setLoan(loanEntity);
            loanPaymentEntity = loanPaymentRepository.save(loanPaymentEntity);

        } catch (org.springframework.dao.DataIntegrityViolationException ex) {
            if (ex.getMostSpecificCause().toString().contains("ConstraintViolation")) {
                throw new ApiException("400", ex.getMostSpecificCause().toString());
            } else {
                throw new ApiException("400", "Invalid Data");
            }
        }
        return modelMapper.map(loanPaymentEntity, PaymentResponse.class);
    }

    @Override
    public List<PaymentResponse> getPayments(Long loanId) {
        List<LoanPaymentEntity> loanPaymentListEntity = loanPaymentRepository.findByLoanId(loanId);
        Type targetListType = new TypeToken<List<PaymentResponse>>() {
        }.getType();
        return modelMapper.map(loanPaymentListEntity, targetListType);
    }


    @Override
    public void delete(Long entityId) {
        LoanEntity entityDataEntity = loanRepository.findOne(entityId);
        checkEntity(entityDataEntity);
        entityDataEntity.setStatus("DELETED");
        loanRepository.save(entityDataEntity);
    }


    private void checkEntity(LoanEntity entityDataEntity) {
        if (entityDataEntity == null) {
            throw new NotFoundException(NOT_FOUND_ERROR_STATUS, NOT_FOUND_ERROR_MESSAGE);
        }
    }
}
