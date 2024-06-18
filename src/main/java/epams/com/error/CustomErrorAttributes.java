package epams.com.error;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@Component
public class CustomErrorAttributes implements ErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
        Map<String, Object> errorAttributes = new HashMap<>();
        errorAttributes.put("status", getAttribute(webRequest, "javax.servlet.error.status_code"));
        errorAttributes.put("error", getAttribute(webRequest, "javax.servlet.error.message"));
        errorAttributes.put("message", getAttribute(webRequest, "javax.servlet.error.message"));
        return errorAttributes;
    }

    private Object getAttribute(WebRequest webRequest, String name) {
        return webRequest.getAttribute(name, WebRequest.SCOPE_REQUEST);
    }

    @Override
    public Throwable getError(WebRequest webRequest) {
        return (Throwable) webRequest.getAttribute("javax.servlet.error.exception", WebRequest.SCOPE_REQUEST);
    }
}