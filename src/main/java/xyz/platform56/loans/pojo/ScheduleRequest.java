package xyz.platform56.loans.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleRequest {

    private Boolean includeSat;
    private Boolean includeSun;
    private Boolean includeHoliday;
    private Date startDate = new Date();
    private int numberOfDays = 50;

}
