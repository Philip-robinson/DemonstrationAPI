package uk.co.rpl.demonstartionapi.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import uk.co.rpl.demonstartionapi.configuration.AppConfig;
import uk.co.rpl.demonstartionapi.controllers.dto.Status;
import uk.co.rpl.demonstartionapi.mapping.StatusMapper;
import uk.co.rpl.demonstartionapi.storage.Store;


@RestController
@AllArgsConstructor
@Slf4j
@Api
public class HomeController {
    private final Store store;
    private final AppConfig config;
    private final StatusMapper statMap;

    @GetMapping(value = {"/", ""})
    @ApiOperation("Get application status")
    public Status getStatus(){
        log.debug("Status requested.");
        return statMap.toStatus(store.status(), config);
    }
}
