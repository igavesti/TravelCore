package id.co.travel.travelcore.exception;

import lombok.Data;
import lombok.Getter;

@Getter
public class CustomException extends Exception{
    private String errorMessage;

    public CustomException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }
}
