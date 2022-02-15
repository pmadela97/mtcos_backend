package com.security.jwt;



import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.user.application.UserApplicationService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final UserApplicationService userApplicationService;
    private final JwtTokenComponent jwtTokenComponent;

    @Autowired
    JwtRequestFilter(UserApplicationService userApplicationService, JwtTokenComponent jwtTokenComponent) {

        this.userApplicationService = userApplicationService;
        this.jwtTokenComponent = jwtTokenComponent;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        final String requestTokenHeader = request.getHeader("Authorization");

        String username = null;
        String jwtToken = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {

            jwtToken = requestTokenHeader.substring(7);

            try {

                username = jwtTokenComponent.getUsernameFromToken(jwtToken);
            }
            catch (IllegalArgumentException e) {

                System.out.println("Unable to get JWT Token");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            }
            catch (ExpiredJwtException e) {

                System.out.println("JWT Token has expired");
                System.out.println(jwtTokenComponent.getExpirationDateFromToken(jwtToken));

                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
            catch (MalformedJwtException e) {
                System.out.println("JWT Token is invalid");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }
        else {

            if (requestTokenHeader == null) {
                logger.warn("JWT Token does not exist");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
            else {

                if (!(requestTokenHeader.startsWith("Bearer ")))
                    logger.warn("JWT Token does not begin with Bearer String");
                response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
            }
        }

        // Once we get the token validate it.
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.userApplicationService.loadUserByUsername(username);

            // if token is valid configure Spring Security to manually set
            // authentication
            if (jwtTokenComponent.validateToken(jwtToken, userDetails)) {

                System.out.println(jwtTokenComponent.getExpirationDateFromToken(jwtToken));
                System.err.println("WAA");

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // After setting the Authentication in the context, we specify
                // that the current user is authenticated. So it passes the
                // Spring Security Configurations successfully.
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);
    }
}
