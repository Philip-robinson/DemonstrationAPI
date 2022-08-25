package uk.co.rpl.demonstartionapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uk.co.rpl.demonstartionapi.controllers.dto.InputProduct;
import uk.co.rpl.demonstartionapi.storage.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ControllerTest {

    private APIController inst;
    private Store service;
    private ExceptionHandlerAdvice excHandler;

    private Product prod_1;
    private Product prod_2;
    private static final String NAME_1 = "The-First-One";
    private static final String NAME_2 = "THE-Other";
    private static final String BAD_NAME = "XXXXX";
    private static final String ERROR_NAME = "YYYYYY";

    private static final String DESC_1 = "The first";
    private static final String DESC_2 = "The second product";
    private static final int VAL_1 = 10000;
    private static final int VAL_2 = 56899;
    private static final int NUMBER_1 = 5000;
    private static final int NUMBER_2 = 20;

    private MockMvc mvc;
    private List<Product> products;
    @BeforeEach
    void setUp() {
        service = mock(Store.class);
        inst = new APIController(service);
        excHandler = new ExceptionHandlerAdvice();

        prod_1 = new Product(NAME_1, DESC_1, VAL_1, NUMBER_1);
        prod_2 = new Product(NAME_2, DESC_2, VAL_2, NUMBER_2);
        products = asList(prod_1, prod_2);
        when(service.getAllNames()).thenReturn(products.stream().map(p->p.getName()).collect(Collectors.toList()));
        when(service.getProduct(NAME_1)).thenReturn(prod_1);
        when(service.getProduct(NAME_2)).thenReturn(prod_2);
        when(service.updateStockLevel(any(), anyInt())).thenReturn(prod_1);
        when(service.getProduct(BAD_NAME)).thenThrow(new ProductDoesNotExistException("Prod none existent"));
        when(service.getProduct(ERROR_NAME)).thenThrow(new ProductAlreadyExistsException("Prod none existent"));
        when(service.status()).thenReturn(true);
        doAnswer(c->{
            InputProduct arg = c.getArgument(0);
            return switch(arg.getName()){
                case NAME_1 -> prod_1;
                case NAME_2 -> prod_2;
                case ERROR_NAME ->
                    throw new ProductAlreadyExistsException("prod already exists");
                default -> throw new IllegalStockLevel("illegal");
            };
        }).when(service).addProduct(any(InputProduct.class));

        mvc = MockMvcBuilders
                .standaloneSetup(inst, excHandler)
                .setControllerAdvice(excHandler)
                .build();
    }

    @Test
    void getStatus() throws Exception {
        mvc.perform(get("/")).
                andExpect(status().is(200)).
                andExpect(jsonPath("$.name").value("Demonstration API")).
                andExpect(jsonPath("$.status").value("Online"));
    }

    @Test
    void getStatusOffline() throws Exception {
        when(service.status()).thenReturn(false);
        mvc.perform(get("/")).
                andExpect(status().is(200)).
                andExpect(jsonPath("$.name").value("Demonstration API")).
                andExpect(jsonPath("$.status").value("Offline"));
    }

    @Test
    void createProduct() throws Exception {
        var body = Map.of("name", NAME_1,
                "description", DESC_1,
                "priceInPence", VAL_1,
                "numberInStock", NUMBER_1);
        var bodyText = new ObjectMapper().writeValueAsString(body);
        mvc.perform(post("/product").
                        contentType(MediaType.APPLICATION_JSON).
                        content(bodyText)).
                andExpect(status().is(200)).
                andExpect(jsonPath("$.name").value(NAME_1)).
                andExpect(jsonPath("$.description").value(DESC_1)).
                andExpect(jsonPath("$.priceInPence").value(VAL_1)).
                andExpect(jsonPath("$.numberInStock").value(NUMBER_1));
    }

    @Test
    void createProductNoName() throws Exception {
        var body = Map.of(
                "description", DESC_1,
                "priceInPence", VAL_1,
                "numberInStock", NUMBER_2);
        var bodyText = new ObjectMapper().writeValueAsString(body);

        mvc.perform(post("/product").
                        contentType(MediaType.APPLICATION_JSON).
                        content(bodyText)).
                andExpect(status().is(400));
    }

    @Test
    void createProductNameTooShort() throws Exception {
        var body = Map.of(
                "name", "A",
                "description", DESC_1,
                "priceInPence", VAL_1,
                "numberInStock", NUMBER_2);
        var bodyText = new ObjectMapper().writeValueAsString(body);

        mvc.perform(post("/product").
                        contentType(MediaType.APPLICATION_JSON).
                        content(bodyText)).
                andExpect(status().is(400));
    }

    @Test
    void createProductNoPrice() throws Exception {
        var body = Map.of(
                "name", NAME_1,
                "description", DESC_1,
                "numberInStock", NUMBER_2);
        var bodyText = new ObjectMapper().writeValueAsString(body);

        mvc.perform(post("/product").
                        contentType(MediaType.APPLICATION_JSON).
                        content(bodyText)).
                andExpect(status().is(400));
    }

    @Test
    void createProductNegativeStock() throws Exception {
        var body = Map.of(
                "name", NAME_1,
                "descripton", DESC_1,
                "priceInPence", VAL_1,
                "numberInStock", -3);
        var bodyText = new ObjectMapper().writeValueAsString(body);

        mvc.perform(post("/product").
                        contentType(MediaType.APPLICATION_JSON).
                        content(bodyText)).
                andExpect(status().is(400));
    }


    @Test
    void createProductNameTooLong() throws Exception {
        var body = Map.of(
                "name", "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890",
                "descripton", DESC_1,
                "priceInPence", VAL_1,
                "numberInStock", NUMBER_2);
        var bodyText = new ObjectMapper().writeValueAsString(body);

        mvc.perform(post("/product").
                        contentType(MediaType.APPLICATION_JSON).
                        content(bodyText)).
                andExpect(status().is(400));
    }

    @Test
    void createProductAlreadyExists() throws Exception {
        var body = Map.of(
                "name", ERROR_NAME,
                "description", DESC_1,
                "priceInPence", VAL_1,
                "numberInStock", NUMBER_2);
        var bodyText = new ObjectMapper().writeValueAsString(body);
        mvc.perform(post("/product").
                        contentType(MediaType.APPLICATION_JSON).
                        content(bodyText)).
                andExpect(status().is(400));
    }


    @Test
    void getProduct() throws Exception {
        mvc.perform(get("/product/"+NAME_2)).
                andExpect(status().is(200)).
                andExpect(jsonPath("$.name").value(NAME_2)).
                andExpect(jsonPath("$.description").value(DESC_2));
        verify(service).getProduct(NAME_2);
        verifyNoMoreInteractions(service);
    }

    @Test
    void getAll() throws Exception {
        mvc.perform(get("/products")).
                andExpect(status().is(200)).
                andDo(print()).
                andExpect(jsonPath("$").isArray()).
                andExpect(jsonPath("$.[0]").value(NAME_1)).
                andExpect(jsonPath("$.[1]").value(NAME_2)).
                andExpect(jsonPath("$.[2]").doesNotExist());
        verify(service).getAllNames();
        verifyNoMoreInteractions(service);
    }

    @Test
    void amendStock() throws Exception {
        var additionalStock = 23;
        var body = Map.of("additionalStock", additionalStock);
        var bodyText = new ObjectMapper().writeValueAsString(body);
        mvc.perform(patch("/product/{name}/amendStock", NAME_1).
                contentType(MediaType.APPLICATION_JSON).
                content(bodyText)).
                andExpect(status().is(200)).
                andExpect(jsonPath("$.name").value(NAME_1));
        verify(service).updateStockLevel(NAME_1, additionalStock);
    }
}