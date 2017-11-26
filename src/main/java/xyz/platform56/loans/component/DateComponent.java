package xyz.platform56.loans.component;

import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class DateComponent {

    public List<LocalDate> generateScheduleDate(LocalDate startDate, int workdays, List<DayOfWeek> excludedDaysWeek, List<LocalDate> holidayDates) {
        List<LocalDate> resultDate = new ArrayList<LocalDate>();

        LocalDate result = startDate;
        int addedDays = 0;
        while (addedDays < workdays) {
            result = result.plusDays(1);
            if (!(checkIfDayIsAllowed(result.getDayOfWeek(), excludedDaysWeek)) && (!checkIfDateIsIncluded(result, holidayDates))) {
                ++addedDays;
                resultDate.add(result);
            }
        }

        return resultDate;
    }


    public boolean checkIfDayIsAllowed(DayOfWeek theCurrentWeekName, List<DayOfWeek> excludedDays) {
        return excludedDays.contains(theCurrentWeekName);
    }

    public boolean checkIfDateIsIncluded(LocalDate theCurrentDay, List<LocalDate> holidayDates) {
        return holidayDates.contains(theCurrentDay);
    }
}
