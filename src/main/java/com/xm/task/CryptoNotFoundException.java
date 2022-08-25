package com.xm.task;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@ResponseStatus(HttpStatus.NOT_FOUND)
public class CryptoNotFoundException extends RuntimeException {
    private String message;

    public CryptoNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
