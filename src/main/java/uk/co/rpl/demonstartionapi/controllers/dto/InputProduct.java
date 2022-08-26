package uk.co.rpl.demonstartionapi.controllers.dto;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@ToString
public class InputProduct {
    @NotNull
    @Size(min=5, max=30)
    private String name;
    @Size(max = 1000)
    private String description;
    @NotNull
    @Min(0)
    private Integer priceInPence;
    @NotNull
    @Min(0)
    private Integer numberInStock;
}
