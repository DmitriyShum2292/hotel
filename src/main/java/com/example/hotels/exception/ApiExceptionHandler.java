package com.example.hotels.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ApiExceptionHandler {

    private final String exc = "exception";

    @ExceptionHandler(value = {NotFoundException.class})
    public ModelAndView notFoundException(NotFoundException e){
        ModelAndView mav = new ModelAndView();
        mav.setStatus(HttpStatus.NOT_FOUND);
        mav.setViewName(exc);
        mav.addObject("status", HttpStatus.NOT_FOUND.toString());
        mav.addObject(exc, e.getMessage());
        return mav;
    }
    @ExceptionHandler(value = {ForbiddenException.class})
    public ModelAndView forbiddenException(ForbiddenException e){
        ModelAndView mav = new ModelAndView();
        mav.setStatus(HttpStatus.FORBIDDEN);
        mav.setViewName(exc);
        mav.addObject("status", HttpStatus.FORBIDDEN.toString());
        mav.addObject("exception", e.getMessage());
        return mav;
    }
}
