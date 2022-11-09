package com.example.ordersystem.security.handler;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        String errorMessage;
        if (exception instanceof BadCredentialsException) {
            errorMessage = "ID or PASSWORD ERROR";
        } else if (exception instanceof InternalAuthenticationServiceException) {
            errorMessage = "System Error!";
        } else if (exception instanceof UsernameNotFoundException) {
            errorMessage = "Not Account, Please resister";
        } else if (exception instanceof AuthenticationCredentialsNotFoundException) {
            errorMessage = "Authetication Reject";
        } else {
            errorMessage = "알 수 없는 이유로 로그인에 실패하였습니다 관리자에게 문의하세요.";
        }
        setDefaultFailureUrl("/login?error=true&exception="+errorMessage);

        super.onAuthenticationFailure(request, response, exception);

    }
}