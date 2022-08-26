package uk.co.rpl.demonstartionapi.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import uk.co.rpl.demonstartionapi.controllers.dto.ChangeStock;
import uk.co.rpl.demonstartionapi.controllers.dto.InputProduct;
import uk.co.rpl.demonstartionapi.controllers.dto.OutputProduct;
import uk.co.rpl.demonstartionapi.storage.Store;

import javax.validation.Valid;
import java.util.List;
import uk.co.rpl.demonstartionapi.mapping.ProductMapper;

@RestController
@AllArgsConstructor
@Slf4j
@Api
@RequestMapping("/api")
public class APIController {
    private final Store store;
    private final ProductMapper mapper;

    @PostMapping("/product")
    @ApiOperation(value="Create a product", authorizations={@Authorization("api-key")})
    public OutputProduct createProduct(@RequestBody @Valid InputProduct product){
        log.debug("Create new product: {}", product);
        return mapper.toOutput(store.addProduct(product));
    }

    @GetMapping("/product/{productName}")
    @ApiOperation(value="get a product", authorizations={@Authorization("api-key")})
    public OutputProduct getProduct(@PathVariable String productName){
        log.debug("Get product named {}", productName);
        return mapper.toOutput(store.getProduct(productName));
    }

    @GetMapping("/products")
    @ApiOperation(value="List all products", authorizations={@Authorization("api-key")})
    public List<String> getAll(){
        log.debug("Get all products");
        return store.getAllNames();
    }

    @PatchMapping("/product/{productName}/amendStock")
    @ApiOperation(value="Modify stock level", authorizations={@Authorization("api-key")})
    public OutputProduct amendStock(@PathVariable String productName,
                                    @RequestBody @Valid ChangeStock changeStock){
        log.debug("add {} additional items of type {} to stock", changeStock, productName);
        return mapper.toOutput(store.updateStockLevel(productName, changeStock.getAdditionalStock()));
    }
}
