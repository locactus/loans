package xyz.platform56.loans.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanDetailsResponse {

    private Long id;

    private String externalRef;

    private String customerRef;

    private String paymentType;

    private String disbursementType;

    private double principal;

    private double interestRate;

    private String status;

    private Date releaseDate;

    private Date createdOn;

    private Date updatedOn;


}
