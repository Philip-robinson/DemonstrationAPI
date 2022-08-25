package uk.co.rpl.demonstartionapi.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import uk.co.rpl.demonstartionapi.controllers.dto.ChangeStock;
import uk.co.rpl.demonstartionapi.controllers.dto.InputProduct;
import uk.co.rpl.demonstartionapi.controllers.dto.OutputProduct;
import uk.co.rpl.demonstartionapi.controllers.dto.Status;
import uk.co.rpl.demonstartionapi.storage.Store;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@Api
public class HomeController {
    private final Store store;

    @GetMapping(value = {"/", ""})
    @ApiOperation("Get application status")
    public Status getStatus(){
        log.debug("Status requested.");
        return new Status(store.status()?"Online":"Offline");
    }
}
