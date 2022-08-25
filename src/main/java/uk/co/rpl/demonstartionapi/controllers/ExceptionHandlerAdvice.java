package uk.co.rpl.demonstartionapi.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import uk.co.rpl.demonstartionapi.storage.IllegalStockLevel;
import uk.co.rpl.demonstartionapi.storage.ProductAlreadyExistsException;
import uk.co.rpl.demonstartionapi.storage.ProductDoesNotExistException;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
@Slf4j
public class ExceptionHandlerAdvice {
    @ExceptionHandler({IllegalStockLevel.class, ProductAlreadyExistsException.class})
    public ResponseEntity<String> handleIllegal(RuntimeException e){
        return new ResponseEntity<>("""
                                    {
                                        "message": "{msg}"
                                    }
                                    """.replace("{msg}", e.getMessage()),
                BAD_REQUEST);
    }

    @ExceptionHandler(ProductDoesNotExistException.class)
    public ResponseEntity<String> handleNoProduct(ProductDoesNotExistException e){
        return new ResponseEntity<>("""
                                    {
                                        "message": "{msg}"
                                    }
                                    """.replace("{msg}", e.getMessage()),
                NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationError(MethodArgumentNotValidException e){
        return new ResponseEntity<>("""
                                    {
                                        "message": "{msg}"
                                    }
                                    """.replace("{msg}", e.getMessage()),
                BAD_REQUEST);

    }
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<String> handleGenException(Throwable e){
        log.error("Received exception {}", e.getClass());
        return new ResponseEntity<>("""
                                    {
                                        "message": "{msg}"
                                    }
                                    """.replace("{msg}", e.getMessage()),
                INTERNAL_SERVER_ERROR);
    }
}
