package xyz.platform56.loans.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "loan_payments")
@lombok.Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoanPaymentEntity extends DomainObject {


    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "external_ref", nullable = false, length = 100)
    private String externalRef;

    @Column(name = "payment_date", nullable = false, length = 100)
    private Date paymentDate;

    @Column(name = "status", nullable = false, length = 100)
    private String status = "INACTIVE";

    @JoinColumn(name = "loan_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "loan_id_pk")
    )
    @ManyToOne
    private LoanEntity loan;



    public enum Paths {
        externalRef
    }

}
