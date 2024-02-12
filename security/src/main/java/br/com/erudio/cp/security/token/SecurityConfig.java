package br.com.erudio.cp.security.token;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private JwtTokenProvider tokenProvider;

	@Bean
	PasswordEncoder passwordEncoder() {
		Map<String, PasswordEncoder> encoders = new HashMap<>();
		
		Pbkdf2PasswordEncoder encod = new Pbkdf2PasswordEncoder("", 8, 185000,
				SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);
		encoders.put("pbkdf2", encod);
		
		DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("pbkdf2", encoders);
		passwordEncoder.setDefaultPasswordEncoderForMatches(encod);

		return passwordEncoder;
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	SecurityFilterChain httpSecurityFilterChain(HttpSecurity http) throws Exception {
		JwtTokenFilter customFilter = new JwtTokenFilter(tokenProvider);

		//@formatter:off
		return http.httpBasic(basic -> basic.disable())
			.csrf(csrf -> csrf.disable())
			.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class)
			.sessionManagement(
					session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // sessao sem estado/não guarda nada
			.authorizeHttpRequests(
					authorize -> authorize
						.requestMatchers(
								"/auth/signin",
								"/auth/refresh/**",
	                    		"/swagger-ui/**",
	                    		"/v3/api-docs/**"
						).permitAll()
						.requestMatchers("/api/**").authenticated()
						.requestMatchers("/users").denyAll()
					
			)
			.cors(cors -> {}) //configurações padrão
			.build();
			
		//@formatter:on
	}

}
