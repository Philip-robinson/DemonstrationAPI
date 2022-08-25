package uk.co.rpl.demonstartionapi.storage;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uk.co.rpl.demonstartionapi.controllers.dto.InputProduct;

import javax.validation.constraints.NotNull;
import java.io.*;
import java.util.*;

@Service
@Slf4j
public class StoreImpl implements Store {

    private Map<String, Product> products = new TreeMap<>();

    @Override
    public Product addProduct(@NotNull InputProduct prod) {
        var ucName = prod.getName().toUpperCase();
        log.debug("Add new product name: {}, description: {}, priceInPence: {}, numberInStock: {}",
                ucName, prod.getDescription(), prod.getPriceInPence(), prod.getNumberInStock());
        if (products.containsKey(ucName))
            throw new ProductAlreadyExistsException(ucName);
        var newProd = new Product(ucName, prod.getDescription(), prod.getPriceInPence(), prod.getNumberInStock());
        products.put(ucName, newProd);
        log.info("Now product added {}", newProd);
        return newProd;
    }

    @Override
    public Product updateStockLevel(@NotNull String name, int additionalStock) {
        var ucName = name.toUpperCase();
        log.debug("Adding stock {} items to {}", additionalStock, ucName);
        var product = products.get(ucName);
        if (product == null) throw new ProductDoesNotExistException(name);
        var newNumberInStock = product.getNumberInStock() + additionalStock;
        if (newNumberInStock < 0)
            throw new IllegalStockLevel("Stock level of " + name + " would be " + newNumberInStock);
        product.setNumberInStock(newNumberInStock);
        log.info("Stock level of {} updated, now {}", ucName, product.getNumberInStock());
        return product;
    }

    @Override
    public Product getProduct(@NotNull String name) {
        var ucName = name.toUpperCase();
        log.debug("Requesting product {}", ucName);
        if (!products.containsKey(ucName)) throw new ProductDoesNotExistException(ucName);
        return products.get(ucName);
    }

    @Override
    public List<String> getAllNames() {
        log.debug("Retrieving all values: {}", products.keySet());
        return new ArrayList<>(products.keySet());
    }

    @Override
    public boolean status() {
        return true;
    }

}
