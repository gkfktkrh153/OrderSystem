package com.example.ordersystem.exhandler.advice;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;

@Slf4j
@RestControllerAdvice
public class ExControllerAdvice
{

    @ExceptionHandler(IllegalArgumentException.class)
    public void illegalExHandler(IllegalArgumentException e, HttpServletResponse response) throws IOException {
        System.out.println("test");

        //String redirect_uri = "redirect:/admin/reservation/resist?error=true&exception=test";
        String redirect_uri = "/admin/reservation/resist?error=true&exception=" + e.getMessage();

        response.sendRedirect(redirect_uri);
    }
}
