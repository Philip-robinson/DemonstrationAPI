package uk.co.rpl.demonstartionapi.storage;

public class IllegalStockLevel extends RuntimeException{
    public IllegalStockLevel(String message) {
        super(message);
    }

    public IllegalStockLevel(String message, Throwable cause) {
        super(message, cause);
    }
}
