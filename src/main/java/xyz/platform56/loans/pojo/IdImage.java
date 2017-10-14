package xyz.platform56.loans.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@lombok.Builder
@AllArgsConstructor
@NoArgsConstructor
public class IdImage {

    private Long id;

    @NotNull(message = "'image url' is required.")
    private String url;
    @NotNull(message = "'image description' is required.")
    private String description;


}
