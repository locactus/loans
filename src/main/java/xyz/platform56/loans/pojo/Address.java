package xyz.platform56.loans.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {


    @NotNull(message = "'street' is required.")
    @NotEmpty(message = "'street' is required.")
    private String street;

    @NotNull(message = "'city' is required.")
    @NotEmpty(message = "'city' is required.")
    private String city;

    @NotNull(message = "'postcode' is required.")
    @NotEmpty(message = "'postcode' is required.")
    private String postcode;

    @NotNull(message = "'state' is required.")
    @NotEmpty(message = "'state' is required.")
    private String state = "";

    @NotNull(message = "'country' is required.")
    @NotEmpty(message = "'country' is required.")
    private String country;
}
