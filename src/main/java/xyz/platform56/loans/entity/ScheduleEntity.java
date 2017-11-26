package xyz.platform56.loans.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "schedules")
@lombok.Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ScheduleEntity extends DomainObject {


    @Column(name = "external_ref", nullable = false, length = 100)
    private String externalRef;

    @Column(name="include_sat", columnDefinition = "tinyint default false")
    private Boolean includeSat = false;

    @Column(name="include_sun", columnDefinition = "tinyint default false")
    private Boolean includeSun = false;

    @Column(name="include_holiday", columnDefinition = "tinyint default false")
    private Boolean includeHoliday = false;

    @Column(name="start_date", nullable = false, length = 100)
    private Date startDate = new Date();

    @Column(name="number_of_days", nullable = false, length = 100)
    private int numberOfDays = 50;

    @Column(name = "principal", nullable = false, length = 100)
    private double principal = 0.0;

    @Column(name = "interest_rate", nullable = false, length = 100)
    private double interestRate = 15.0;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "single_schedule_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "single_schedule_id_pk")
    )
    private List<SingleLineScheduleEntity> singleLineSchedules;

    public enum Paths {
        loanRef
    }

}
