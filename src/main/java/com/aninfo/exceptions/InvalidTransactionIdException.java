package com.aninfo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class InvalidTransactionIdException extends RuntimeException {

    public InvalidTransactionIdException(String message) {
        super(message);
    }
}
