package xyz.platform56.loans.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@lombok.Builder
@AllArgsConstructor
@NoArgsConstructor
public class Identification {

    private Long id;

    @NotNull(message = "'id type' is required.")
    private String type;

    @NotNull(message = "'id number' is required.")
    private String idNumber;

    @Valid
    @Size(min = 1)
    private Set<IdImage> images;


}
