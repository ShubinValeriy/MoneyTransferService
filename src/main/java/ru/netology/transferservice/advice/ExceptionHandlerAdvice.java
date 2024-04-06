package ru.netology.transferservice.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.netology.transferservice.exception.ErrorTransferConfirmException;
import ru.netology.transferservice.exception.InvalidTransferDataException;
import ru.netology.transferservice.logger.Logger;
import ru.netology.transferservice.logger.SimpleLogger;


import java.util.concurrent.atomic.AtomicInteger;

@RestControllerAdvice
public class ExceptionHandlerAdvice {
    Logger logger;
    AtomicInteger errorId;

    public ExceptionHandlerAdvice() {
        logger = SimpleLogger.getInstance();
        errorId = new AtomicInteger(0);
    }

    @ExceptionHandler(InvalidTransferDataException.class)
    public ResponseEntity<ErrorResponse> handler(InvalidTransferDataException e) {
        logger.log(e.getMessage());
        return new ResponseEntity<>(
                new ErrorResponse(
                        "BAD_REQUEST : " + e,
                        errorId.incrementAndGet()
                        ),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(ErrorTransferConfirmException.class)
    public ResponseEntity<ErrorResponse> handler(ErrorTransferConfirmException e) {
        logger.log(e.getMessage());
        return new ResponseEntity<>(
                new ErrorResponse(
                        "INTERNAL_SERVER_ERROR : " + e,
                        errorId.incrementAndGet()
                ),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
