package xyz.platform56.loans.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "line_schedules")
@lombok.Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SingleLineScheduleEntity extends DomainObject {


    @Column(name = "payment_date", nullable = true, length = 100)
    private Date paymentDate;

    @Column(name = "payment_on", nullable = true, length = 100)
    private Date paidOn;

    @Column(name = "amount", nullable = true, length = 100)
    private Double amount;

    @Column(name = "status", nullable = true, length = 100)
    private String status;

    @JoinColumn(name = "schedule_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "single_schedule_schedule_id_pk")
    )
    @ManyToOne
    private ScheduleEntity schedule;


    public enum Paths {
        loanRef
    }

}
