package br.com.erudio.cp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RequiredObjectIsNullException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RequiredObjectIsNullException() {
		super("A null object is not allowed");
	}

	public RequiredObjectIsNullException(String message) {
		super(message);
	}

	public RequiredObjectIsNullException(String message, Throwable cause) {
		super(message, cause);
	}

}
