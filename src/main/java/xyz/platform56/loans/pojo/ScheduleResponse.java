package xyz.platform56.loans.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Date startDate;
    private int numberOfDays;
    private List<LineSchedule> schedules;

}
