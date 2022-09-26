package pl.coderslab.exception;

import java.sql.SQLException;

public class IdNotFoundException extends SQLException {
    public IdNotFoundException(String message){
        super(message);
    }
}
