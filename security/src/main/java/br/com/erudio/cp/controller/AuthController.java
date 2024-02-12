package br.com.erudio.cp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.erudio.cp.data.vo_v1.security.AccountCredentialsVO;
import br.com.erudio.cp.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Athentication Endpoint")
@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private AuthService authService;
	
	@Operation(summary="Authenticates a user and return a token")
	@PostMapping("/signin")
	public ResponseEntity<?> signin(@RequestBody AccountCredentialsVO data) {
		if(creckIfParamsIsNotNull(data)) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request");
		
		var token = authService.signin(data);
		if(token == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request");
		
		return token;
	}
	
	@Operation(summary="Refrest token for return a token")
	@PutMapping("/refresh/{username}")
	public ResponseEntity<?> refreshToken(@PathVariable String username, @RequestHeader("Authorization") String refreshToken) {
		if(creckIfParamsIsNotNull(username, refreshToken)) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request");
		
		var token = authService.refreshToken(username, refreshToken);
		if(token == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request");
		
		return token;
	}

	private boolean creckIfParamsIsNotNull(String username, String refreshToken) {
		return username !=  null && username.isBlank() && refreshToken !=  null && refreshToken.isBlank();
	}

	private boolean creckIfParamsIsNotNull(AccountCredentialsVO data) {
		return data == null || data.getUsername() == null || data.getUsername().isBlank()
				|| data.getPassword() == null || data.getPassword().isBlank();
	}

}
