package br.com.erudio.cp.security.token;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.erudio.cp.exception.InvalidJWTAuthenticationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtTokenFilter extends OncePerRequestFilter {

	@Autowired
	private JwtTokenProvider provider;

	public JwtTokenFilter(JwtTokenProvider provider) {
		this.provider = provider;
	}

//	@Override
//	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//			throws IOException, ServletException {
//		String token = provider.resolveToken((HttpServletRequest) request);
//
//		if (token != null && provider.validateToken(token)) {
//			Authentication auth = provider.getAuthentication(token);
//
//			if (auth != null) {
//				SecurityContextHolder.getContext().setAuthentication(auth); // Setta a authenticação na sessão do
//			}
//		}
//
//		chain.doFilter(request, response);
//	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = provider.resolveToken((HttpServletRequest) request);

		try {
			if (token != null && provider.validateToken(token)) {
				Authentication auth = provider.getAuthentication(token);

				if (auth != null) {
					SecurityContextHolder.getContext().setAuthentication(auth); // Setta a authenticação na sessão do
				}
			}
		} catch (InvalidJWTAuthenticationException e) {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.getWriter().write(e.getLocalizedMessage());
			response.getWriter().flush();
			return;
		}

		filterChain.doFilter(request, response);

	}

}
