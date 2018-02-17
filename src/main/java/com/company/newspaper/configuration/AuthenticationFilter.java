package com.company.newspaper.configuration;

import com.company.newspaper.model.entities.UserSession;
import com.company.newspaper.repository.UserSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

public class AuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserSessionRepository userSessionRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header != null) {

            UserSession userSession = userSessionRepository.getBySessionId(header);
            if (userSession == null) {
                throw new UsernameNotFoundException("No such user");
            }


            UserDetails userDetails = new User(
                    userSession.getUser().getUsername(),
                    userSession.getUser().getPassword(),
                    true,
                    true,
                    true,
                    true,
                    userSession.getUser().getRoles().stream()
                            .map(r -> new SimpleGrantedAuthority(r.getRoleName()))
                            .collect(Collectors.toList()));

            UsernamePasswordAuthenticationToken securityUserToken =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

            SecurityContextHolder.getContext().setAuthentication(securityUserToken);
        }
        filterChain.doFilter(request, response);
    }
}
