package epams.framework.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import java.util.NoSuchElementException;
/*
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handleException(Exception ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        return new ModelAndView("error/404"); // 'src/main/resources/templates/error/500.html'
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleNotFound(NoSuchElementException ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        return new ModelAndView("error/404"); // 'src/main/resources/templates/error/404.html'
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ModelAndView handleAccessDenied(AccessDeniedException ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        return new ModelAndView("error/403"); // 'src/main/resources/templates/error/403.html'
    }
}
*/