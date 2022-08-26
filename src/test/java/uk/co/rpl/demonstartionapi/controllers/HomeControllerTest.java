package uk.co.rpl.demonstartionapi.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uk.co.rpl.demonstartionapi.storage.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import uk.co.rpl.demonstartionapi.configuration.AppConfig;
import uk.co.rpl.demonstartionapi.controllers.dto.Status;
import uk.co.rpl.demonstartionapi.mapping.StatusMapper;

class HomeControllerTest {
    private static final String VERSION = "VERSION";
    private static final String TIME = "TIME";
    private static final String BRANCH = "BRANCH";

    private Store store;
    private AppConfig config;
    private HomeController inst;
    private ExceptionHandlerAdvice excHandler;

    private MockMvc mvc;
    @BeforeEach
    void setUp() {
        excHandler = new ExceptionHandlerAdvice();
        store = mock(Store.class);
        config = mock(AppConfig.class);
        when(config.getBranch()).thenReturn(BRANCH);
        when(config.getBuildTime()).thenReturn(TIME);
        when(config.getVersion()).thenReturn(VERSION);
        inst = new HomeController(store, config, Mappers.getMapper(StatusMapper.class));
        mvc = MockMvcBuilders
                .standaloneSetup(inst, excHandler)
                .setControllerAdvice(excHandler)
                .build();
    }

    @Test
    void getStatus() throws Exception {
        when(store.status()).thenReturn(true);
        mvc.perform(get("/")).
                andExpect(status().is(200)).
                andExpect(jsonPath("$.name").value("Demonstration API")).
                andExpect(jsonPath("$.status").value("Online")).
                andExpect(jsonPath("$.branch").value(BRANCH)).
                andExpect(jsonPath("$.version").value(VERSION)).
                andExpect(jsonPath("$.buildTime").value(TIME)).
                andExpect(jsonPath("$.name").value("Demonstration API"));
    }

    @Test
    void getStatusOffline() throws Exception {
        when(store.status()).thenReturn(false);
        mvc.perform(get("/")).
                andExpect(status().is(200)).
                andExpect(jsonPath("$.name").value("Demonstration API")).
                andExpect(jsonPath("$.status").value("Offline")).
                andExpect(jsonPath("$.branch").value(BRANCH)).
                andExpect(jsonPath("$.version").value(VERSION)).
                andExpect(jsonPath("$.buildTime").value(TIME)).
                andExpect(jsonPath("$.name").value("Demonstration API"));
    }

}