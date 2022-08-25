package uk.co.rpl.demonstartionapi.controllers.dto;

import lombok.*;
import uk.co.rpl.demonstartionapi.storage.Product;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OutputProduct {
    private String name;
    private String description;
    private int priceInPence;
    private int numberInStock;

    public static OutputProduct create(Product product){
        return new OutputProduct(
                product.getName(),
                product.getDescription(),
                product.getPriceInPence(),
                product.getNumberInStock()
        );
    }
}
