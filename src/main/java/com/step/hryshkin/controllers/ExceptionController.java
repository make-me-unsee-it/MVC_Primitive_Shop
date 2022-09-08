package com.step.hryshkin.controllers;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionController {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String getNotFoundPage(NoHandlerFoundException e) {
        return "errors/404";
    }

    @GetMapping("/404")
    public String statusNotFound(HttpServletRequest request) {
        return "errors/404";
    }

    @GetMapping("/403")
    public String statusForbidden(HttpServletRequest request) {
        return "errors/403";
    }
}
