package xyz.platform56.loans.service;

import com.google.common.collect.Lists;
import org.modelmapper.ModelMapper;
import xyz.platform56.loans.entity.AddressEntity;
import xyz.platform56.loans.entity.CustomerEntity;
import xyz.platform56.loans.entity.IdentificationEntity;
import xyz.platform56.loans.exception.ApiException;
import xyz.platform56.loans.exception.NotFoundException;
import xyz.platform56.loans.pojo.LoanDetails;
import xyz.platform56.loans.pojo.Identification;
import xyz.platform56.loans.pojo.PaginationSearchRequest;
import xyz.platform56.loans.pojo.SearchResponse;

import xyz.platform56.loans.repository.AddressRepository;
import xyz.platform56.loans.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import xyz.platform56.loans.repository.IdentificationRepository;
import xyz.platform56.loans.utils.ModelMapperBasedTransformer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
@Slf4j
public class LoanServiceImpl extends AbstractService implements LoanService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private IdentificationRepository identificationRepository;


    @Autowired
    private ModelMapper modelMapper;


    @Autowired
    @Qualifier("customerEntityCustomerModelMapperBasedTransformer")
    private ModelMapperBasedTransformer<CustomerEntity, LoanDetails> customerEntityCustomerModelMapperBasedTransformer;

    @Autowired
    @Qualifier("identificationEntityIdentificationPojoModelMapperBasedTransformer")
    private ModelMapperBasedTransformer<IdentificationEntity, Identification>
            identificationEntityIdentificationPojoModelMapperBasedTransformer;


    private static final String NOT_FOUND_ERROR_STATUS = "Not Found";
    private static final String NOT_FOUND_ERROR_MESSAGE = "No LoanDetails Found";


    @Override
    public SearchResponse search(String customerName, PaginationSearchRequest searchRequest) {
        SearchResponse<CustomerEntity> searchResponse = customerRepository.search(customerName, searchRequest);
        return searchResponseConverter.buildSearchResponse(searchResponse, customerEntityCustomerModelMapperBasedTransformer);
    }

    @Override
    public LoanDetails get(Long entityId) {
        CustomerEntity customerEntity = customerRepository.findOne(entityId);
        checkEntity(customerEntity);
        return modelMapper.map(customerEntity, LoanDetails.class);
    }

    @Override
    public LoanDetails create(LoanDetails request) {
        CustomerEntity customerEntity = modelMapper.map(request, CustomerEntity.class);
        try {

            customerEntity = customerRepository.save(customerEntity);
            AddressEntity addressEntity = modelMapper.map(request.getAddress(), AddressEntity.class);
            addressEntity = addressRepository.save(addressEntity);
            addressEntity.setCustomer(customerEntity);
            customerEntity.setPrimaryAddress(addressEntity);
            customerEntity = customerRepository.save(customerEntity);

        } catch (org.springframework.dao.DataIntegrityViolationException ex) {
            if (ex.getMostSpecificCause().toString().contains("ConstraintViolation")) {
                throw new ApiException("400", ex.getMostSpecificCause().toString());
            } else {
                throw new ApiException("400", "Invalid Data");
            }
        }
        return modelMapper.map(customerEntity, LoanDetails.class);
    }

    @Override
    public LoanDetails update(Long entityId, LoanDetails request) {
        CustomerEntity customerEntity = customerRepository.findOne(entityId);
        checkEntity(customerEntity);
        customerEntity = modelMapper.map(request, CustomerEntity.class);
        AddressEntity addressEntity = modelMapper.map(request.getAddress(), AddressEntity.class);
        addressEntity = addressRepository.save(addressEntity);
        addressEntity.setCustomer(customerEntity);
        customerEntity.setPrimaryAddress(addressEntity);
        customerEntity = customerRepository.save(customerEntity);
        return modelMapper.map(customerEntity, LoanDetails.class);
    }

    @Override
    public Identification createId(Long customerId, Identification request) {
        CustomerEntity customerEntity = customerRepository.findOne(customerId);
        checkEntity(customerEntity);

        IdentificationEntity identificationEntity = modelMapper.map(request, IdentificationEntity.class);
        identificationEntity.setCustomer(customerEntity);
        identificationEntity = identificationRepository.save(identificationEntity);
       if ( customerEntity.getIdentifications() == null)
       {
           customerEntity.setIdentifications(new ArrayList<>());
           customerEntity.getIdentifications().add(identificationEntity);
       } else {
           customerEntity.getIdentifications().add(identificationEntity);
       }

        customerRepository.save(customerEntity);
        return modelMapper.map(identificationEntity, Identification.class);
    }

    @Override
    public List<Identification> fetchIds(Long customerId) {
        CustomerEntity customerEntity = customerRepository.findOne(customerId);
        checkEntity(customerEntity);
        List<IdentificationEntity> identifications = customerEntity.getIdentifications();
        if (identifications == null) {
            return Collections.emptyList();
        }
        return Lists.transform(identifications, identificationEntityIdentificationPojoModelMapperBasedTransformer);
    }

    @Override
    public void delete(Long entityId) {
        CustomerEntity entityDataEntity = customerRepository.findOne(entityId);
        checkEntity(entityDataEntity);
        customerRepository.delete(entityId);
    }

    private void checkEntity(CustomerEntity entityDataEntity) {
        if (entityDataEntity == null) {
            throw new NotFoundException(NOT_FOUND_ERROR_STATUS, NOT_FOUND_ERROR_MESSAGE);
        }
    }
}
