package uk.co.rpl.demonstartionapi.storage;

public class ProductDoesNotExistException extends RuntimeException{
    public ProductDoesNotExistException(String message) {
        super(message);
    }

    public ProductDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
