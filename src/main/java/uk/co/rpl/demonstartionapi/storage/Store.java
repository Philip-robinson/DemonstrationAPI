package uk.co.rpl.demonstartionapi.storage;

import uk.co.rpl.demonstartionapi.controllers.dto.InputProduct;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;

public interface Store {
   Product addProduct(@NotNull InputProduct prod);

   Product updateStockLevel(@NotNull String name, int additionalStock);

   Product getProduct(@NotNull String name);

   List<String> getAllNames();

   boolean status();
}
