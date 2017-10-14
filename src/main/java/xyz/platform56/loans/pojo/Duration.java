package xyz.platform56.loans.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Duration {
    private int durationNumber;
    //Daily, Weekly, Monthly, Yearly
    private int durationType;
}
