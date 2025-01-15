package com.hexaware.QuitQApplication.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private JwtTokenProvider jwtTokenProvider;
	private UserDetailsService customUserDetails;

	public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, UserDetailsService customUserDetails) {
		this.jwtTokenProvider = jwtTokenProvider;
		this.customUserDetails = customUserDetails;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// Get JWT token from HTTP request
		String token = getTokenFromRequest(request);

		// Validate token
		if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
			// Get username from token
			String username = jwtTokenProvider.getUsername(token);

			// Load user details from username
			UserDetails userDetails = customUserDetails.loadUserByUsername(username);

			// Create authentication token
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
					userDetails, null, userDetails.getAuthorities());

			// Set the authentication details
			authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

			// Set authentication in SecurityContext
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		}

		// Continue with the filter chain
		filterChain.doFilter(request, response);
	}

	/**
	 * Extracts the JWT token from the request's Authorization header.
	 *
	 * @param request The HTTP request
	 * @return The JWT token, or null if not present
	 */
	private String getTokenFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");

		// Check if the token is in the format "Bearer <token>"
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7); // Remove "Bearer " prefix and return token
		}

		return null;
	}
}
