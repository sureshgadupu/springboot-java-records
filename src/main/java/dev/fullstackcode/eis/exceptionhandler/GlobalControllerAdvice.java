package dev.fullstackcode.eis.exceptionhandler;

import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ResponseBody
@ControllerAdvice
public class GlobalControllerAdvice {

    public record ErrorResponse(String error, int code, List<ErrorMessageDto> errors) {

    }


    public record ErrorMessageDto(String object, String field, String message, Object rejectedValue) {
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {

        List<ErrorMessageDto> validationErrorDetails = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(this::mapToErrorMessageDto)
                .collect(Collectors.toList());

        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST.name(), HttpStatus.BAD_REQUEST.value(), validationErrorDetails);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private ErrorMessageDto mapToErrorMessageDto(ObjectError error) {
        String fieldError = "";
        Object rejectedValue = "";
        if (error instanceof FieldError) {
            fieldError = ((FieldError) error).getField();
            rejectedValue = ((FieldError) error).getRejectedValue();
        }
        return new ErrorMessageDto(error.getObjectName(), fieldError, error.getDefaultMessage(), rejectedValue);
    }
}