package xyz.platform56.loans.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {

    @NotNull(message = "'amount' is required.")
    private Double amount;

    @NotNull(message = "'externalRef' is required.")
    private String externalRef;

    @NotNull(message = "'paymentDate' is required.")
    private Date paymentDate;

}
