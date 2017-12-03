package xyz.platform56.loans.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "loans")
@lombok.Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoanEntity extends DomainObject {


    @Column(name = "external_ref", nullable = false, length = 100)
    private String externalRef;

    @Column(name = "customer_ref", nullable = false, length = 100)
    private String customerRef;

    @Column(name = "payment_type", nullable = false, length = 100)
    private String paymentType = "CASH";

    @Column(name = "disbursement_type", nullable = false, length = 100)
    private String disbursementType = "CASH";

    @Column(name = "principal", nullable = false, length = 100)
    private double principal;

    @Column(name = "interest_rate", nullable = false, length = 100)
    private double interestRate;

    @Column(name = "status", nullable = false, length = 100)
    private String status = "INACTIVE";

    @Column(name = "release_date", length = 100)
    private Date releaseDate;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "loan_payment_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "loan_payment_id_pk")
    )
    private List<LoanPaymentEntity> loanPayments;


    public enum Paths {
        loanRef
    }

}
