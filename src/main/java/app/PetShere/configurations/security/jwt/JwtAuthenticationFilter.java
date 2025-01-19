package app.PetShere.configurations.security.jwt;

import app.PetShere.services.JwtService;
import app.PetShere.utils.Constants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            final String token = getTokenFromRequest(request);
            if (token == null) {
                log.warn(Constants.JWT_NOT_FOUND_MSG);
                filterChain.doFilter(request, response);
                return;
            }

            final String username = jwtService.getUsernameFromToken(token);
            if (username == null) {
                log.warn(Constants.JWT_EMAIL_NOT_FOUND_MSG);
                filterChain.doFilter(request, response);
                return;
            }

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (!jwtService.isTokenValid(token, userDetails)) {
                SecurityContextHolder.clearContext();
                filterChain.doFilter(request, response);
                return;
            }

            if (jwtService.isTokenExpired(token) && jwtService.canTokenBeRenewed(token)) {
                String newToken = jwtService.renewToken(token, userDetails);
                response.setHeader(HttpHeaders.AUTHORIZATION, Constants.BEARER_START + newToken);
                authenticateUser(userDetails.getUsername(), userDetails, request);
            } else {
                SecurityContextHolder.clearContext();
            }

            authenticateUser(userDetails.getUsername(), userDetails, request);

        } catch (Exception exception) {
            log.error(Constants.JWT_PROCESSING_ERROR + "{}", exception.getMessage());
        } finally {
            filterChain.doFilter(request, response);
        }
    }

    private void authenticateUser(String username, UserDetails userDetails, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                username,
                null,
                userDetails.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.hasText(authHeader) && authHeader.startsWith(Constants.BEARER_START)) {
            return authHeader.substring(Constants.BEARER_START.length());
        }

        return null;
    }
}
