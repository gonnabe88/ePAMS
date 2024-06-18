package epams.com.error;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomErrorController {

    @GetMapping("/error/400")
    public String error400() {
        return "/common/error/400";
    }

    @GetMapping("/error/401")
    public String error401() {
        return "/common/error/401";
    }

    @GetMapping("/error/403")
    public String error403() {
        return "/common/error/403";
    }

    @GetMapping("/error/404")
    public String error404() {
        return "/common/error/404";
    }

    @GetMapping("/error/500")
    public String error500() {
        return "/common/error/500";
    }

    @GetMapping("/error")
    public String handleError() {
        return "/common/error/genericError";
    }
}