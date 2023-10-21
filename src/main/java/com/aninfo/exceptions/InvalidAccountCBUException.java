package com.aninfo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class InvalidAccountCBUException extends RuntimeException {

    public InvalidAccountCBUException(String message) {
        super(message);
    }
}

