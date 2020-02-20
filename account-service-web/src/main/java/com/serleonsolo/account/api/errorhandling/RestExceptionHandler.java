package com.serleonsolo.account.api.errorhandling;

import com.serleonsolo.account.exception.AccountException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class RestExceptionHandler {

    @ExceptionHandler({AccountException.class})
    ResponseEntity<?> handleAccountException(AccountException e) {
        log.error("Process exception ", e);

        return ResponseEntity.status(e.getHttpStatus())
                .body(e.getMessage());
    }
}
