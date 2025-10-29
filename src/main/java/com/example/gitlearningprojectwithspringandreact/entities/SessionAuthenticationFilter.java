package com.example.gitlearningprojectwithspringandreact.entities;


import com.example.gitlearningprojectwithspringandreact.services.sessionService.SessionServiceImpl;
import com.example.gitlearningprojectwithspringandreact.services.userService.UserServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class SessionAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private SessionServiceImpl sessionService;

    @Autowired
    private UserServiceImpl userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String sessionId = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("sessionId".equals(cookie.getName())) {
                    sessionId = cookie.getValue();
                    break;
                }
            }
        }

        if (sessionId != null) {
            Optional<Session> session = sessionService.getSession(sessionId);
            if (session.isPresent()) {
                User user = userService.findByEmail(session.get().getPrincipalName())
                        .orElse(null);

                if (user != null) {
                    GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());
                    Authentication auth = new UsernamePasswordAuthenticationToken(
                            user,              // <-- теперь кладём целого User
                            null,
                            List.of(authority)
                    );

                    SecurityContextHolder.getContext().setAuthentication(auth);
                }

            }
        }

        filterChain.doFilter(request, response);
    }
}