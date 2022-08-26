package uk.co.rpl.demonstartionapi.controllers.dto;

import lombok.*;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OutputProduct {
    private String name;
    private String description;
    private int priceInPence;
    private int numberInStock;
}
