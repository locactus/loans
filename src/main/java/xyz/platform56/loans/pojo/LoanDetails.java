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
public class LoanDetails {

    private Long id;

    @NotNull(message = "'externalRef' is required.")
    @NotEmpty(message = "'externalRef' is required.")
    private String externalRef;

    @NotNull(message = "'customerRef' is required.")
    @NotEmpty(message = "'customerRef' is required.")
    private String customerRef;

    private String loanProductRef;

    private String paymentType;

    @Valid
    private Principal principal;

    @Valid
    private Duration duration;

    private Date createdOn;

    private Date updatedOn;


}
