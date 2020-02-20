package com.serleonsolo.account.exception;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;

public class ExceptionFactory {

    @Contract("_ -> new")
    @NotNull
    public static AccountException notFoundError(String message) {
        return new AccountException(message, HttpStatus.NOT_FOUND);
    }

    @Contract("_ -> new")
    @NotNull
    public static AccountException badRequestError(String message) {
        return new AccountException(message, HttpStatus.BAD_REQUEST);
    }
}
