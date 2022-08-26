package uk.co.rpl.demonstartionapi.mapping;

import org.mapstruct.Mapper;
import uk.co.rpl.demonstartionapi.controllers.dto.OutputProduct;
import uk.co.rpl.demonstartionapi.storage.Product;

/**
 *
 * @author philip
 */
@Mapper(componentModel="spring")
public interface ProductMapper {

    OutputProduct toOutput(Product p);

}
