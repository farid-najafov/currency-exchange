package com.xe.exception;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Controller
public class GlobalErrorHandler implements ErrorController {

  @Override
  public String getErrorPath() {
    return "/error";
  }

  @RequestMapping("/error")
  @ExceptionHandler(Exception.class)
  public ModelAndView handleError(HttpServletRequest request, Exception ex) {
    Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
    ModelAndView mv = new ModelAndView();
    if (status != null) {
      Integer statusCode = Integer.valueOf(status.toString());

      if(statusCode == HttpStatus.NOT_FOUND.value()) {
        mv.setViewName("error-404");
        return mv;
      }
      if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
        mv.setViewName("error-500");
        return mv;
      }
    }
    mv.addObject("exception", ex);
    mv.addObject("url", request.getRequestURL());
    mv.setViewName("error");
    return mv;
  }
}
