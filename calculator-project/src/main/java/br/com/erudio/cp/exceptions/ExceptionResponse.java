package br.com.erudio.cp.exceptions;

import java.io.Serializable;
import java.security.Timestamp;
import java.time.Instant;

public class ExceptionResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private Instant instant;
	private String message;
	private String details;

	public ExceptionResponse(Instant instant, String message, String details) {
		super();
		this.instant = instant;
		this.message = message;
		this.details = details;
	}

	public Instant getInstant() {
		return instant;
	}

	public void setInstant(Instant instant) {
		this.instant = instant;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

}
