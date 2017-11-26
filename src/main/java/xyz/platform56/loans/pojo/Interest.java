package xyz.platform56.loans.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Interest {
    // Flat Rate
    private String method;
    // Percentage, Fixed Amount
    private String interestType;

    // principal or percentage
    private Double interest;

}
