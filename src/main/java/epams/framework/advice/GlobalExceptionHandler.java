package epams.framework.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.NoSuchElementException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    // @ExceptionHandler(Exception.class)
    // @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    // public ModelAndView handleException(Exception ex, Model model) {
    //     model.addAttribute("error", ex.getMessage());
    //     return new ModelAndView("error/404"); // 'src/main/resources/templates/error/500.html'
    // }

    // @ExceptionHandler(NoSuchElementException.class)
    // @ResponseStatus(HttpStatus.NOT_FOUND)
    // public ModelAndView handleNotFound(NoSuchElementException ex, Model model) {
    //     model.addAttribute("error", ex.getMessage());
    //     return new ModelAndView("error/404"); // 'src/main/resources/templates/error/404.html'
    // }

    // @ExceptionHandler(AccessDeniedException.class)
    // @ResponseStatus(HttpStatus.FORBIDDEN)
    // public ModelAndView handleAccessDenied(AccessDeniedException ex, Model model) {
    //     model.addAttribute("error", ex.getMessage());
    //     return new ModelAndView("error/403"); // 'src/main/resources/templates/error/403.html'
    // }

 @ExceptionHandler(NullPointerException.class)
public ResponseEntity<String> handleNullPointerException(NullPointerException ex) {
    // 예외가 발생한 스택 트레이스를 순회하면서 첫 번째 Controller 클래스와 메서드명을 찾음
    String controllerInfo = getControllerMethodInfo(ex);

    // 예외 메시지에 Controller명, 메서드명, 줄 번호를 포함하여 반환
    String message = String.format("NullPointerException occurred in %s: %s", controllerInfo, ex.getMessage());
    log.error(message);

    // 404 Not Found 상태 코드와 빈 응답을 반환
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
}

@ExceptionHandler(Exception.class)
public ResponseEntity<String> handleException(Exception ex) {
    // 예외가 발생한 스택 트레이스를 순회하면서 첫 번째 Controller 클래스와 메서드명을 찾음
    String controllerInfo = getControllerMethodInfo(ex);

    // 예외 메시지에 Controller명, 메서드명, 줄 번호를 포함하여 반환
    String message = String.format("An error occurred in %s: %s", controllerInfo, ex.getMessage());
    log.error(message);

    // 500 Internal Server Error 상태 코드와 빈 응답을 반환
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("");
}

/**
 * 예외가 발생한 스택 트레이스를 순회하면서 Controller 클래스와 메서드명을 찾음
 */
private String getControllerMethodInfo(Exception ex) {
    for (StackTraceElement element : ex.getStackTrace()) {
        try {
            Class<?> clazz = Class.forName(element.getClassName());
            if (clazz.isAnnotationPresent(RestController.class) || clazz.isAnnotationPresent(Controller.class)) {
                return String.format("%s.%s() at line %d",
                        clazz.getSimpleName(), element.getMethodName(), element.getLineNumber());
            }
        } catch (ClassNotFoundException e) {
            // 예외 처리 중 클래스 로드에 실패한 경우, 아무 것도 하지 않음 (continue loop)
        }
    }
    // Controller 정보를 찾지 못한 경우 기본 메시지 반환
    return "Unknown Controller";
}
    
}
