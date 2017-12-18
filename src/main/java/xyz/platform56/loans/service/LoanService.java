package xyz.platform56.loans.service;

import xyz.platform56.loans.pojo.*;

import java.util.List;

public interface LoanService {

    SearchResponse search(String loanRef, String status, PaginationSearchRequest searchRequest);

    LoanDetailsResponse get(Long loanId);

    LoanDetailsResponse create(LoanDetailsRequest request);

    LoanDetailsResponse update(Long loanId, LoanDetailsRequest request);

    ScheduleResponse createSchedule(Long loanId, ScheduleRequest request);

    ScheduleResponse previewSchedule(ScheduleRequest request);

    ScheduleResponse fetchSchedule(Long loanId);

    PaymentResponse payLoan(Long loanId, PaymentRequest request);

    List<PaymentResponse> getPayments(Long loanId);

    void delete(Long loanId);
}
