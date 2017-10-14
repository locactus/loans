package xyz.platform56.loans.repository;

import xyz.platform56.loans.entity.CustomerEntity;
import xyz.platform56.loans.pojo.PaginationSearchRequest;
import xyz.platform56.loans.pojo.SearchResponse;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepositoryCustom {

    SearchResponse<CustomerEntity> search(String customerName, PaginationSearchRequest searchRequest);

}
