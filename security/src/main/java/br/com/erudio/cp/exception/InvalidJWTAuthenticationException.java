package br.com.erudio.cp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class InvalidJWTAuthenticationException extends AuthenticationException {

	private static final long serialVersionUID = 1L;

	public InvalidJWTAuthenticationException(String message) {
		super(message);
	}

	public InvalidJWTAuthenticationException(String message, Throwable cause) {
		super(message, cause);
	}

}
