package com.raficruz.crudcliente.handler;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.raficruz.crudcliente.handler.exception.BusinessException;
import com.raficruz.crudcliente.handler.exception.NotFoundException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class ExceptionsHandler {

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ApiErrorResponse> handleNotFoundException(NotFoundException ex) {
		ApiErrorResponse responseMsg = new ApiErrorResponse(1, ex.getMessage(), ex.getLocalizedMessage(), null);
		return new ResponseEntity<>(responseMsg, HttpStatus.NO_CONTENT);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiErrorResponse> handleException(MethodArgumentNotValidException ex) {
		BindingResult result = ex.getBindingResult();
		List<FieldError> fieldErrors = result.getFieldErrors();
		List<String> errors = fieldErrors.stream()
								.map(e -> MessageFormat.format("{0} inválido: {1}. {2}", 
										e.getField(), e.getRejectedValue(),
										e.getDefaultMessage()))
								.collect( Collectors.toList());

		return new ResponseEntity<>(new ApiErrorResponse(2, "Erro(s) de validação", null, errors), HttpStatus.CONFLICT);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ApiErrorResponse> handleException(HttpMessageNotReadableException ex) {
		
		ApiErrorResponse responseMsg = new ApiErrorResponse(3, ex.getCause().getClass().getSimpleName(), ex.getLocalizedMessage(), null);
		return new ResponseEntity<>(responseMsg, HttpStatus.CONFLICT);

	}

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ApiErrorResponse> handleBusinessException(BusinessException ex) {
		ApiErrorResponse responseMsg = new ApiErrorResponse(1, ex.getMessage(), ex.getDescription(), null);
		return new ResponseEntity<>(responseMsg, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiErrorResponse> handleException(Exception ex) {
		ApiErrorResponse responseMsg = new ApiErrorResponse(0, ex.getMessage(), ex.getLocalizedMessage(), null);
		return new ResponseEntity<>(responseMsg, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
