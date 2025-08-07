package com.valentino.blog_app.utils;

import com.valentino.blog_app.repository.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private JwtUtils jwtUtils;
    private IUserRepository userRepository;

    public CustomOAuth2SuccessHandler(JwtUtils jwtService, IUserRepository userRepository) {
        this.jwtUtils = jwtService;
        this.userRepository = userRepository;
    }


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        AuthenticationSuccessHandler.super.onAuthenticationSuccess(request, response, chain, authentication);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        String token = jwtUtils.createToken(authentication);

        // Redirigimos al frontend con el token en la URL
        response.sendRedirect("http://localhost:4200/oauth/redirect?token=" + token);

    }
}
