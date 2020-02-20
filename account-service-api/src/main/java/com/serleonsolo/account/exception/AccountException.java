package com.serleonsolo.account.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class AccountException
        extends Exception {

    @Getter
    private HttpStatus httpStatus;

    public AccountException(String message) {
        super(message);
    }

    public AccountException(String message,
                            HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
