package xyz.platform56.loans.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Local;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScheduleResponse {

    private Long id;
    private Boolean includeSat;
    private Boolean includeSun;
    private Boolean includeHoliday;
    private LocalDate startDate;
    private int numberOfDays;
    private double principal;
    private double interestRate;
    private double totalAmount;
    private List<LineSchedule> schedules;

}
