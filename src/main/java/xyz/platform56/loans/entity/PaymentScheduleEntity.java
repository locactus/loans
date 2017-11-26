package xyz.platform56.loans.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "principal")
@lombok.Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentScheduleEntity extends DomainObject {

    @Column(name = "scheduled_date")
    private Date scheduledDate;

    @Column(name = "paymentDate")
    private Date paymentDate;

}
