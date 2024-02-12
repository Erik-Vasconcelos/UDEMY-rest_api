package br.com.erudio.cp.controller.exception;

import java.io.Serializable;
import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExceptionReponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer statusCode;
	private String message;
	private String details;
	private Instant timestamp;

}
