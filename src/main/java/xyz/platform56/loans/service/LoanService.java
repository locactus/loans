package xyz.platform56.loans.service;

import xyz.platform56.loans.pojo.LoanDetails;
import xyz.platform56.loans.pojo.Identification;
import xyz.platform56.loans.pojo.PaginationSearchRequest;
import xyz.platform56.loans.pojo.SearchResponse;

import java.util.List;

public interface LoanService {

    SearchResponse search(String customerName, PaginationSearchRequest searchRequest);

    LoanDetails get(Long customerId);

    LoanDetails create(LoanDetails request);

    LoanDetails update(Long customerId, LoanDetails request);

    Identification createId(Long customerId, Identification request);

    List<Identification> fetchIds(Long customerId);

    void delete(Long customerId);
}
