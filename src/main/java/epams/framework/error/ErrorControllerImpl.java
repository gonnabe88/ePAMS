package epams.framework.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * Error 페이지 처리 Controller
 *
 * @author 140024
 * @since 2024-08-18
 */
@Slf4j
@Controller
public class ErrorControllerImpl implements ErrorController {

    /**
     * @implNote /error 페이지 처리
     * @author 140024
     * @since 2024-08-18
     */
    @RequestMapping("/error")
    public String handleError() {
        // 사용자 정의 오류 페이지 템플릿을 반환합니다
        return "error/404";
    }

}
