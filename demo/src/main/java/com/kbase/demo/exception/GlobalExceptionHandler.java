package com.kbase.demo.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.util.HashMap;
import java.util.Map;



@RestControllerAdvice
public class GlobalExceptionHandler {



    // Không có quyền
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDenied(
            AccessDeniedException e
    ) {


        ErrorResponse error =
                new ErrorResponse(
                        403,
                        e.getMessage()
                );


        return new ResponseEntity<>(
                error,
                HttpStatus.FORBIDDEN
        );

    }





    // Lỗi Validation @Valid
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(
            MethodArgumentNotValidException e
    ) {


        Map<String,String> errors =
                new HashMap<>();


        e.getBindingResult()
                .getFieldErrors()
                .forEach(error -> {

                    errors.put(
                            error.getField(),
                            error.getDefaultMessage()
                    );

                });



        ErrorResponse response =
                new ErrorResponse(
                        400,
                        "Validation failed",
                        errors
                );



        return new ResponseEntity<>(
                response,
                HttpStatus.BAD_REQUEST
        );

    }





    // Runtime Exception
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntime(
            RuntimeException e
    ) {


        ErrorResponse error =
                new ErrorResponse(
                        400,
                        e.getMessage()
                );


        return new ResponseEntity<>(
                error,
                HttpStatus.BAD_REQUEST
        );

    }

}