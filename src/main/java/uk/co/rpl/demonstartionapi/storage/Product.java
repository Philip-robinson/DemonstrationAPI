package uk.co.rpl.demonstartionapi.storage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
@Builder
public class Product {
    private String name;
    private String description;
    private int priceInPence;
    private int numberInStock;
}
