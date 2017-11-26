package xyz.platform56.loans.controller;


import xyz.platform56.loans.pojo.*;
import xyz.platform56.loans.service.LoanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RequestMapping("/api/v1")
@RestController
@Slf4j
public class LoanController extends BaseController {


    private LoanService loanService;

    @Autowired
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @RequestMapping(value = "loans", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<SearchResponse> search(
            @RequestParam(value = "loanRef", required = false, defaultValue = "") String loanRef,
            @ModelAttribute @Valid PaginationSearchRequest searchRequest) {
        return new ResponseEntity<SearchResponse>(loanService.search(loanRef, searchRequest), HttpStatus.OK);
    }

    @RequestMapping(value = "loans/{loanId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<LoanDetailsResponse> get(@PathVariable Long loanId) {
        return new ResponseEntity<LoanDetailsResponse>(loanService.get(loanId), HttpStatus.OK);
    }

    @RequestMapping(value = "loans/{loanId}/schedules", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ScheduleResponse> createSchedule(@PathVariable Long loanId,
                                                          @RequestBody @Valid ScheduleRequest request ) {
        return new ResponseEntity<ScheduleResponse>(loanService.createSchedule(loanId, request), HttpStatus.OK);
    }

    @RequestMapping(value = "loans/schedules/preview", method = RequestMethod.OPTIONS)
    @ResponseBody
    public ResponseEntity<ScheduleResponse> previewSchedule(@RequestBody @Valid ScheduleRequest request ) {
        return new ResponseEntity<ScheduleResponse>(loanService.previewSchedule(request), HttpStatus.OK);
    }

    @RequestMapping(value = "loans/{loanId}/ids", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ScheduleResponse> fetchSchedule(@PathVariable Long loanId) {
        return new ResponseEntity<ScheduleResponse>(loanService.fetchSchedule(loanId), HttpStatus.OK);
    }

    @RequestMapping(value = "loans", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<LoanDetailsResponse> create(@RequestBody @Valid LoanDetailsRequest request) {
        return new ResponseEntity<LoanDetailsResponse>(loanService.create(request), HttpStatus.CREATED);
    }

    @RequestMapping(value = "loans/{loanId}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<LoanDetailsResponse> update(@PathVariable Long loanId, @RequestBody @Valid LoanDetailsRequest request) {
        return new ResponseEntity<LoanDetailsResponse>(loanService.update(loanId, request), HttpStatus.OK);
    }

    @RequestMapping(value = "loans/{loanId}", method = RequestMethod.DELETE)
    @ResponseBody
    public Object delete(@PathVariable Long loanId) {
        loanService.delete(loanId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}