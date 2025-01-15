package com.hexaware.QuitQApplication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.hexaware.QuitQApplication.security.JwtAuthenticationEntryPoint;
import com.hexaware.QuitQApplication.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final JwtAuthenticationEntryPoint authenticationEntryPoint;
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final UserDetailsService userDetailsService;

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	public SecurityConfig(JwtAuthenticationEntryPoint authenticationEntryPoint,
			JwtAuthenticationFilter jwtAuthenticationFilter, UserDetailsService userDetailsService) {
		this.authenticationEntryPoint = authenticationEntryPoint;
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
		this.userDetailsService = userDetailsService;

	}

	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.cors().and().csrf().disable().authorizeHttpRequests(auth -> auth.requestMatchers("/api/auth/**").permitAll() // Open
																														// access
																														// for
																														// authentication
//				authenticated().
				.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // Allow preflight requests globally
				.requestMatchers("/api/**").permitAll().requestMatchers("/api/admin/**").hasRole("ADMIN") // Admin-specific
																														// endpoints
				.requestMatchers("/api/seller/**").hasRole("SELLER") // Seller-specific endpoints
				.requestMatchers("/api/customer/**").hasRole("CUSTOMER") // Customer-specific endpoints
				.anyRequest().authenticated()) // All other endpoints require authentication
				.exceptionHandling(ex -> ex.authenticationEntryPoint(authenticationEntryPoint)) // Handle unauthorized
																								// access
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		return httpSecurity.build();
	}
}
