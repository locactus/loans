package xyz.platform56.loans.service;

import com.google.common.collect.Lists;
import org.modelmapper.ModelMapper;
import xyz.platform56.loans.component.DateComponent;
import xyz.platform56.loans.entity.LoanEntity;
import xyz.platform56.loans.exception.ApiException;
import xyz.platform56.loans.exception.NotFoundException;
import xyz.platform56.loans.pojo.*;

import xyz.platform56.loans.repository.AddressRepository;
import xyz.platform56.loans.repository.LoanRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import xyz.platform56.loans.repository.IdentificationRepository;
import xyz.platform56.loans.utils.ModelMapperBasedTransformer;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class LoanServiceImpl extends AbstractService implements LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private IdentificationRepository identificationRepository;


    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private DateComponent dateComponent;


    @Autowired
    @Qualifier("customerEntityCustomerModelMapperBasedTransformer")
    private ModelMapperBasedTransformer<LoanEntity, LoanDetails> customerEntityCustomerModelMapperBasedTransformer;


    private static final String NOT_FOUND_ERROR_STATUS = "Not Found";
    private static final String NOT_FOUND_ERROR_MESSAGE = "No LoanDetails Found";


    @Override
    public SearchResponse search(String customerName, PaginationSearchRequest searchRequest) {
        SearchResponse<LoanEntity> searchResponse = loanRepository.search(customerName, searchRequest);
        return searchResponseConverter.buildSearchResponse(searchResponse, customerEntityCustomerModelMapperBasedTransformer);
    }

    @Override
    public LoanDetails get(Long entityId) {
        LoanEntity loanEntity = loanRepository.findOne(entityId);
        checkEntity(loanEntity);
        return modelMapper.map(loanEntity, LoanDetails.class);
    }

    @Override
    public LoanDetails create(LoanDetails request) {
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
        return modelMapper.map(loanEntity, LoanDetails.class);
    }

    @Override
    public LoanDetails update(Long entityId, LoanDetails request) {
        LoanEntity loanEntity = loanRepository.findOne(entityId);
        checkEntity(loanEntity);
        loanEntity = modelMapper.map(request, LoanEntity.class);
        ;
        loanEntity = loanRepository.save(loanEntity);
        return modelMapper.map(loanEntity, LoanDetails.class);
    }

    @Override
    public ScheduleResponse createSchedule(Long loanId, ScheduleRequest request) {
        return null;
    }

    @Override
    public ScheduleResponse previewSchedule(Long loanId, ScheduleRequest request) {
        LocalDate startDate = request.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        List<DayOfWeek> dayOfWeeks = Lists.newArrayList();
        dayOfWeeks.add(DayOfWeek.SATURDAY);
        dayOfWeeks.add(DayOfWeek.SUNDAY);
        List<LocalDate> holidayDates = Lists.newArrayList();
        List<LocalDate> localDates = dateComponent.generateScheduleDate(startDate, request.getNumberOfDays(),
                dayOfWeeks, holidayDates);

        List<LineSchedule> lineSchedules = Lists.newArrayList();

        for (LocalDate localDate : localDates) {
            lineSchedules.add(LineSchedule.builder().paymentDate(localDate).build());
        }

        return ScheduleResponse.builder().
                includeHoliday(request.getIncludeHoliday()).
                includeSat(request.getIncludeSat()).
                includeSun(request.getIncludeSun()).
                schedules(lineSchedules)
                .build();
    }


    @Override
    public ScheduleResponse fetchSchedule(Long loanId) {
        return null;
    }


    @Override
    public void delete(Long entityId) {
        LoanEntity entityDataEntity = loanRepository.findOne(entityId);
        checkEntity(entityDataEntity);
        loanRepository.delete(entityId);
    }


    private void checkEntity(LoanEntity entityDataEntity) {
        if (entityDataEntity == null) {
            throw new NotFoundException(NOT_FOUND_ERROR_STATUS, NOT_FOUND_ERROR_MESSAGE);
        }
    }
}
