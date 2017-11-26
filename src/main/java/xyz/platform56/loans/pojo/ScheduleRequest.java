package xyz.platform56.loans.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleRequest {

    private Boolean includeSat = false;
    private Boolean includeSun = false;
    private Boolean includeHoliday = false;
    private Date startDate = new Date();
    private int numberOfDays = 50;
    private double principal = 0.0;
    private double interestRate = 15.0;

}
