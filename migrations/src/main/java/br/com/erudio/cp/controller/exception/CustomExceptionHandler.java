package br.com.erudio.cp.controller.exception;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.erudio.cp.exception.ObjectNotFoundException;

@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionReponse> genericError(Exception ex, WebRequest request) {
		ExceptionReponse reponse = new ExceptionReponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(),
				request.getDescription(false), Instant.now());

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(reponse);

	}

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<ExceptionReponse> ObjectNotFoundError(Exception ex, WebRequest request) {
		ExceptionReponse reponse = new ExceptionReponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(),
				request.getDescription(false), Instant.now());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(reponse);
	}
}
