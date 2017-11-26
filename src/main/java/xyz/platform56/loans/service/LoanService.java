package xyz.platform56.loans.service;

import xyz.platform56.loans.pojo.*;

import java.util.List;

public interface LoanService {

    SearchResponse search(String customerName, PaginationSearchRequest searchRequest);

    LoanDetails get(Long loanId);

    LoanDetails create(LoanDetails request);

    LoanDetails update(Long loanId, LoanDetails request);

    ScheduleResponse createSchedule(Long loanId, ScheduleRequest request);

    ScheduleResponse previewSchedule(ScheduleRequest request);

    ScheduleResponse fetchSchedule(Long loanId);

    void delete(Long loanId);
}
