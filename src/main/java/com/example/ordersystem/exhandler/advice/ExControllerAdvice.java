package com.example.ordersystem.exhandler.advice;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;

@Slf4j
@RestControllerAdvice
public class ExControllerAdvice
{

    @ExceptionHandler(IllegalArgumentException.class)
    public void illegalExHandler(IllegalArgumentException e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("test");
        System.out.println("에러1: "+e.getMessage());
        String referer = request.getHeader("referer");
        System.out.println(referer);
        //String redirect_uri = "redirect:/admin/reservation/resist?error=true&exception=test";
        //String redirect_uri = "/admin/reservation/resist?error=true&exception=" + e.getMessage();
        String param = URLEncoder.encode(e.getMessage(),"UTF-8");
        String redirect_uri = referer + "?error=true&exception=" + param;
        response.sendRedirect(redirect_uri);
    }
}
