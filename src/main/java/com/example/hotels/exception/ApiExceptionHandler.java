package com.example.hotels.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {NotFoundException.class})
    public ModelAndView notFoundException(NotFoundException e){
        ModelAndView mav = new ModelAndView();
        mav.setStatus(HttpStatus.NOT_FOUND);
        mav.setViewName("exception");
        mav.addObject("status", HttpStatus.NOT_FOUND.toString());
        mav.addObject("exception", e.getMessage());
        return mav;
    }
}
