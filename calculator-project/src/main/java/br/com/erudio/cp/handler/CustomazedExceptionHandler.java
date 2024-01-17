package br.com.erudio.cp.handler;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.erudio.cp.exceptions.ExceptionResponse;
import br.com.erudio.cp.exceptions.UnsupportedMathOperationException;

@RestControllerAdvice
public class CustomazedExceptionHandler extends ResponseEntityExceptionHandler {

	//TODO - Ajustar o timestamp
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ExceptionResponse> handlerAllException(Exception ex, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(Instant.now(), ex.getMessage(),
				request.getDescription(false));

		return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public final ResponseEntity<ExceptionResponse> handlerIllegalArgumentException(Exception ex, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(Instant.now(), ex.getMessage(),
				request.getDescription(false));

		return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(UnsupportedMathOperationException.class)
	public final ResponseEntity<ExceptionResponse> handlerBadRequestException(Exception ex, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(Instant.now(), ex.getMessage(),
				request.getDescription(false));

		return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}

}
