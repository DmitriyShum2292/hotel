package com.example.hotels.exception;


import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ApiExceptionHandler {

    private static final String EXC = "exception";
    private static final String STATUS = "status";

    @ExceptionHandler(value = {NotFoundException.class})
    public ModelAndView notFoundException(NotFoundException e){
        ModelAndView mav = new ModelAndView();
        mav.setStatus(HttpStatus.NOT_FOUND);
        mav.setViewName(EXC);
        mav.addObject(STATUS, HttpStatus.NOT_FOUND.toString());
        mav.addObject(EXC, e.getMessage());
        return mav;
    }
    @ExceptionHandler(value = {ForbiddenException.class})
    public ModelAndView forbiddenException(ForbiddenException e){
        ModelAndView mav = new ModelAndView();
        mav.setStatus(HttpStatus.FORBIDDEN);
        mav.setViewName(EXC);
        mav.addObject(STATUS, HttpStatus.FORBIDDEN.toString());
        mav.addObject(EXC, e.getMessage());
        return mav;
    }
    @ExceptionHandler(value = {JSONException.class})
    public ModelAndView jsonParsingException(JsonParsingException e){
        ModelAndView mav = new ModelAndView();
        mav.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        mav.setViewName(EXC);
        mav.addObject(STATUS, HttpStatus.INTERNAL_SERVER_ERROR.toString());
        mav.addObject(EXC, e.getMessage());
        return mav;
    }
}
