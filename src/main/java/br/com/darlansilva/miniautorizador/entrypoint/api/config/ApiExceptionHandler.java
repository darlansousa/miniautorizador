package br.com.darlansilva.miniautorizador.entrypoint.api.config;

import java.util.List;
import java.util.stream.StreamSupport;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import br.com.darlansilva.miniautorizador.core.exception.CardAlreadyExistisException;
import br.com.darlansilva.miniautorizador.core.exception.CardNotFoundException;
import br.com.darlansilva.miniautorizador.core.exception.NotAuthenticatedException;
import br.com.darlansilva.miniautorizador.core.exception.NotAuthorizedTransaction;
import br.com.darlansilva.miniautorizador.core.exception.UseCaseException;
import br.com.darlansilva.miniautorizador.entrypoint.api.dto.output.CardOutputDto;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.log4j.Log4j2;

@ControllerAdvice
@Log4j2
public class ApiExceptionHandler {

    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public InvalidRequestExceptionResponse onServerWebInputException(NoResourceFoundException ex) {
        return new InvalidRequestExceptionResponse("RESOURCE_NOT_FOUND", List.of());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public InvalidRequestExceptionResponse onMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        final var properties = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> new ErrorProperty(fieldError.getField(), fieldError.getDefaultMessage()))
                .toList();

        return new InvalidRequestExceptionResponse("METHOD_NOT_VALID", properties);
    }

    @ExceptionHandler(NotAuthenticatedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public InvalidRequestExceptionResponse onNotAuthenticatedException(NotAuthenticatedException ex) {
        return new InvalidRequestExceptionResponse(ex.getMessage(), List.of());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public InvalidRequestExceptionResponse onConstraintViolationException(ConstraintViolationException ex) {
        final var properties = ex.getConstraintViolations().stream().map(violation -> {
            final var field = String.format("%s",
                    StreamSupport
                            .stream(violation.getPropertyPath().spliterator(), false)
                            .reduce((first, second) -> second)
                            .orElse(null));
            return new ErrorProperty(field, violation.getMessage());
        }).toList();
        return new InvalidRequestExceptionResponse("CONSTRAINT_VIOLATION", properties);
    }

    @ExceptionHandler(UseCaseException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    public InvalidRequestExceptionResponse onUseCaseException(UseCaseException ex) {
        return new InvalidRequestExceptionResponse(ex.getMessage(), List.of());
    }

    @ExceptionHandler(CardAlreadyExistisException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    public CardOutputDto onUseCaseException(CardAlreadyExistisException ex) {
        return CardOutputDto.builder().numeroCartao(ex.getCardNumber()).senha(ex.getPassword()).build();
    }

    @ExceptionHandler(NotAuthorizedTransaction.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    public String onNotAuthorizedTransaction(NotAuthorizedTransaction ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(CardNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public void onCardNotFoundException(CardNotFoundException ex) {
        log.error("Card not found", ex);
    }

    public record InvalidRequestExceptionResponse(String code, List<ErrorProperty> properties) {

    }

    public record ErrorProperty(String field, String violation) {

    }

}
