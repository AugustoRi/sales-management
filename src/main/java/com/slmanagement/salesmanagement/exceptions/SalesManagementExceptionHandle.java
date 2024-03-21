package com.slmanagement.salesmanagement.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

// this check if exists exception handle for the error in runtime
@ControllerAdvice
public class SalesManagementExceptionHandle extends ResponseEntityExceptionHandler {
    private static final String CONSTANT_VALIDATION_NOT_BLANK = "NotBlank";
    private static final String CONSTANT_VALIDATION_LENGTH = "Length";
    private static final String CONSTANT_VALIDATION_NOT_NULL = "NotNull";
    private static final String CONSTANT_VALIDATION_PATTERN = "Pattern";
    private static final String CONSTANT_VALIDATION_MIN = "Min";

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<Error> errors = handleGenerateErrorList(ex.getBindingResult());

        return handleExceptionInternal(ex, errors, headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex, WebRequest request) {
        String msgToUser = "Recurso não encontrado";
        String msgToDeveloper = ex.toString();
        List<Error> errors = Arrays.asList(new Error(msgToUser, msgToDeveloper));

        return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
        String msgToUser = "Recurso não encontrado";
        String msgToDeveloper = ex.toString();
        List<Error> errors = Arrays.asList(new Error(msgToUser, msgToDeveloper));

        return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<Object> handleBusinessRuleException(BusinessRuleException ex, WebRequest request) {
        String msgToUser = ex.getMessage();
        String msgToDeveloper = ex.getMessage();
        List<Error> errors = Arrays.asList(new Error(msgToUser, msgToDeveloper));

        return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    private List<Error> handleGenerateErrorList(BindingResult bindingResult) {
        List<Error> errors = new ArrayList<Error>();
        bindingResult.getFieldErrors().forEach(fieldError -> {
            String msgToUser = handleFormatMsgToUser(fieldError);
                    fieldError.getDefaultMessage();
            String msgToDeveloper = fieldError.toString();

            errors.add(new Error(msgToUser, msgToDeveloper));
        });

        return errors;
    }

    private String handleFormatMsgToUser(FieldError fieldError) {
        switch (Objects.requireNonNull(fieldError.getCode())) {
            case CONSTANT_VALIDATION_NOT_BLANK:
            case CONSTANT_VALIDATION_NOT_NULL:
                return Objects.requireNonNull(fieldError.getDefaultMessage()).concat(" é obrigatório");
            case CONSTANT_VALIDATION_LENGTH:
                return Objects.requireNonNull(fieldError.getDefaultMessage()).concat(String.format(" deve ter entre %s e %s caracteres",
                        Objects.requireNonNull(fieldError.getArguments())[2], fieldError.getArguments()[1]));
            case CONSTANT_VALIDATION_PATTERN:
                return Objects.requireNonNull(fieldError.getDefaultMessage()).concat(" formato inválido");
            case CONSTANT_VALIDATION_MIN:
                return Objects.requireNonNull(fieldError.getDefaultMessage()).concat(String.format(" deve ser maior ou igual a %s",
                        Objects.requireNonNull(fieldError.getArguments())[1]));
            default:
                return fieldError.toString();
        }
    }

}
