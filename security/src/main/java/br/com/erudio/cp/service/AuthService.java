package br.com.erudio.cp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.erudio.cp.data.vo_v1.security.AccountCredentialsVO;
import br.com.erudio.cp.data.vo_v1.security.TokenVO;
import br.com.erudio.cp.repository.UserRepository;
import br.com.erudio.cp.security.token.JwtTokenProvider;

@Service
public class AuthService {

	@Autowired
	private AuthenticationManager manager; // Responsável por buscar um provedor de authentication adequado

	@Autowired
	private JwtTokenProvider provider;

	@Autowired
	private UserRepository repository;

	public ResponseEntity<TokenVO> signin(AccountCredentialsVO data) {
		try {
			String username = data.getUsername();
			String password = data.getPassword();

			manager.authenticate(new UsernamePasswordAuthenticationToken(username, password)); // Inicia o processo de
																								// autenticação

			var user = repository.findByUsername(username);

			var tokenResponse = new TokenVO();
			if (user != null) {
				tokenResponse = provider.createAccessToken(username, user.getRoles());
			} else {
				throw new UsernameNotFoundException("Username " + username + " not foud!");
			}

			return ResponseEntity.ok(tokenResponse);
		} catch (Exception e) {
			throw new BadCredentialsException("Invalid username/password supplied!");
		}
	}

	public ResponseEntity<TokenVO> refreshToken(String username, String refreshToken) {
		var user = repository.findByUsername(username);

		var tokenResponse = new TokenVO();
		if (user != null) {
			tokenResponse = provider.refreshToken(refreshToken);
		} else {
			throw new UsernameNotFoundException("Username " + username + " not foud!");
		}

		return ResponseEntity.ok(tokenResponse);

	}

}
