package br.com.erudio.cp.security.token;

import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import br.com.erudio.cp.data.vo_v1.security.TokenVO;
import br.com.erudio.cp.exception.InvalidJWTAuthenticationException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtTokenProvider {

	@Value("${security.jwt.token.secret-key:secret}")
	private String secretKey = "secret";

	@Value("${security.jwt.token.expire-length:360000}")
	private Long validityInMilliseconds = 360000L;

	@Autowired
	private UserDetailsService userDetailsService;

	Algorithm algorithm = null;

	@PostConstruct // executa o metodo depois da inicialização do spring e antes de qualquer
					// operação de usuário
	protected void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
		algorithm = Algorithm.HMAC256(secretKey.getBytes());// Qual algoritmo vai ser usado para realizar codificação do
															// token
	}

	public TokenVO createAccessToken(String username, List<String> roles) {
		Date now = new Date();
		Date validity = new Date(now.getTime() + validityInMilliseconds);
		var accessToken = getAccessToken(username, roles, now, validity);
		var refreshToken = getRefreshToken(username, roles, now);

		return new TokenVO(username, true, now, validity, accessToken, refreshToken);
	}
	
	public TokenVO refreshToken(String refreshToken) {
		if(refreshToken.contains("Bearer ")) refreshToken = refreshToken.substring("Bearer ".length());
		
		DecodedJWT decodedJWT = decodedToken(refreshToken);
		
		String username = decodedJWT.getSubject();
		List<String> roles = decodedJWT.getClaim("roles").asList(String.class);
		
		return createAccessToken(refreshToken, roles);
	}

	private String getAccessToken(String username, List<String> roles, Date now, Date validity) {
		String issuerUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toString();

		return JWT.create().withClaim("roles", roles).withIssuedAt(now).withExpiresAt(validity).withSubject(username)
				.withIssuer(issuerUrl).sign(algorithm).strip();
	}

	private String getRefreshToken(String username, List<String> roles, Date now) {
		Date validityRefreshToken = new Date(now.getTime() + (validityInMilliseconds * 3));

		return JWT.create().withClaim("roles", roles).withIssuedAt(now).withExpiresAt(validityRefreshToken)
				.withSubject(username).sign(algorithm).strip();
	}

	public Authentication getAuthentication(String token) {
		DecodedJWT decoded = decodedToken(token);

		UserDetails user = userDetailsService.loadUserByUsername(decoded.getSubject());
		return new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
	}

	private DecodedJWT decodedToken(String token) {
		try {
			Algorithm alg = Algorithm.HMAC256(secretKey.getBytes());
			JWTVerifier verifier = JWT.require(alg).build();
			DecodedJWT decodedJWT = verifier.verify(token);

			return decodedJWT;
		} catch (Exception e) {
			throw new InvalidJWTAuthenticationException("Invalid token");
		}
	}

	public String resolveToken(HttpServletRequest request) {
		String bearer = request.getHeader("Authorization");

		// Bearer
		// eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZWFuZHJvIiwicm9sZXMiOlsiQURNSU4iLCJNQU5BR0VSIl0sImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODA4MCIsImV4cCI6MTY1MjcxOTUzOCwiaWF0IjoxNjUyNzE1OTM4fQ.muu8eStsRobqLyrFYLHRiEvOSHAcss4ohSNtmwWTRcY
		if (bearer != null && bearer.startsWith("Bearer ")) {
			return bearer.substring("Bearer ".length());
		}

		return null;
	}

	public boolean validateToken(String token) {
		try {
			DecodedJWT decoded = decodedToken(token);

			if (decoded.getExpiresAt().before(new Date())) {
				return false;
			}
			return true;

		} catch (Exception e) {
			throw new InvalidJWTAuthenticationException("Expired or invalid token");
		}
	}

}
