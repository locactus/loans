package xyz.platform56.loans.controller;


import xyz.platform56.loans.pojo.LoanDetails;
import xyz.platform56.loans.pojo.Identification;
import xyz.platform56.loans.pojo.PaginationSearchRequest;
import xyz.platform56.loans.pojo.SearchResponse;
import xyz.platform56.loans.service.LoanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/v1")
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
            @RequestParam(value = "customerName", required = false, defaultValue = "") String customerName,
            @ModelAttribute @Valid PaginationSearchRequest searchRequest) {
        return new ResponseEntity<SearchResponse>(loanService.search(customerName, searchRequest), HttpStatus.OK);
    }

    @RequestMapping(value = "loans/{loanId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<LoanDetails> get(@PathVariable Long customerId) {
        return new ResponseEntity<LoanDetails>(loanService.get(customerId), HttpStatus.OK);
    }

    @RequestMapping(value = "loans/{customerId}/ids", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Identification> addIdentifications(@PathVariable Long customerId,
                                                       @RequestBody @Valid Identification request ) {
        return new ResponseEntity<Identification>(loanService.createId(customerId, request), HttpStatus.OK);
    }


    @RequestMapping(value = "loans/{customerId}/ids", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Identification>> fetchIdentifications(@PathVariable Long customerId) {
        return new ResponseEntity<List<Identification>>(loanService.fetchIds(customerId), HttpStatus.OK);
    }


    @RequestMapping(value = "loans", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<LoanDetails> create(@RequestBody @Valid LoanDetails request) {
        return new ResponseEntity<LoanDetails>(loanService.create(request), HttpStatus.CREATED);
    }

    @RequestMapping(value = "loans/{customerId}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<LoanDetails> update(@PathVariable Long customerId, @RequestBody @Valid LoanDetails request) {
        return new ResponseEntity<LoanDetails>(loanService.update(customerId, request), HttpStatus.OK);
    }

    @RequestMapping(value = "loans/{customerId}", method = RequestMethod.DELETE)
    @ResponseBody
    public Object delete(@PathVariable Long customerId) {
        loanService.delete(customerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}