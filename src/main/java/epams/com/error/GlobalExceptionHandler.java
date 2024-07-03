package epams.com.error;

import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(Exception.class)
//    public ModelAndView handleAllExceptions(HttpServletRequest request, Exception ex) {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.addObject("exception", ex);
//        modelAndView.addObject("url", request.getRequestURL());
//        modelAndView.setViewName("/common/error/500");
//        return modelAndView;
//    }
}
