package xyz.platform56.loans.repository;

import xyz.platform56.loans.entity.LoanEntity;
import xyz.platform56.loans.pojo.PaginationSearchRequest;
import xyz.platform56.loans.pojo.SearchResponse;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepositoryCustom {

    SearchResponse<LoanEntity> search(String customerName, PaginationSearchRequest searchRequest);

}
