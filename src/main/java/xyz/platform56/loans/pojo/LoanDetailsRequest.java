package xyz.platform56.loans.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanDetailsRequest {

    @NotNull(message = "'externalRef' is required.")
    @NotEmpty(message = "'externalRef' is required.")
    private String externalRef;

    @NotNull(message = "'customerRef' is required.")
    @NotEmpty(message = "'customerRef' is required.")
    private String customerRef;

    private String paymentType;

    private String disbursementType;

    private double principal;

    private double interestRate;

    private Date releaseDate;



}
