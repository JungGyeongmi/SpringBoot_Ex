package org.zerock.clubex.security.handler;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Log4j2
public class ClubAccessDeniedHandler implements AccessDeniedHandler, AuthenticationFailureHandler{
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.error("Access Denied Handler");
        log.error("Redirect.........");
        log.error("requst Context Path "+request.getContextPath());
        response.sendRedirect(request.getContextPath()+"/accessError");

    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        log.error("Authentication Failure Handler");
        log.error("Redirect..........");
        response.sendRedirect(request.getContextPath()+"/accessError");

    }
}