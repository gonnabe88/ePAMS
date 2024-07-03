package epams.com.error;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import lombok.NoArgsConstructor;

/**
 * @author K140024
 * @implNote CustomErrorAttributes 클래스는 에러 속성을 커스터마이징하기 위해 사용됩니다.
 * @since 2024-06-11
 */
@NoArgsConstructor
@Component
public class CustomErrorAttributes implements ErrorAttributes {

    /**
     * 에러 속성을 가져옵니다.
     * 
     * @param webRequest 웹 요청 객체
     * @param options 에러 속성 옵션
     * @return 에러 속성 맵
     * @since 2024-06-11
     */
    @Override
    public Map<String, Object> getErrorAttributes(final WebRequest webRequest, final ErrorAttributeOptions options) {
        final Map<String, Object> errorAttributes = new ConcurrentHashMap<>();
        putIfNotNull(errorAttributes, "status", getAttribute(webRequest, "javax.servlet.error.status_code"));
        putIfNotNull(errorAttributes, "error", getAttribute(webRequest, "javax.servlet.error.message"));
        putIfNotNull(errorAttributes, "message", getAttribute(webRequest, "javax.servlet.error.message"));
        return errorAttributes;
    }

    /**
     * 요청에서 특정 속성을 가져옵니다.
     * 
     * @param webRequest 웹 요청 객체
     * @param name 속성 이름
     * @return 속성 값
     * @since 2024-06-11
     */
    private Object getAttribute(final WebRequest webRequest, final String name) {
        return webRequest.getAttribute(name, WebRequest.SCOPE_REQUEST);
    }

    /**
     * 에러를 가져옵니다.
     * 
     * @param webRequest 웹 요청 객체
     * @return Throwable 객체
     * @since 2024-06-11
     */
    @Override
    public Throwable getError(final WebRequest webRequest) {
        return (Throwable) webRequest.getAttribute("javax.servlet.error.exception", WebRequest.SCOPE_REQUEST);
    }

    /**
     * 값이 null이 아닌 경우 맵에 추가합니다.
     * 
     * @param map 맵 객체
     * @param key 키
     * @param value 값
     * @since 2024-06-11
     */
    private void putIfNotNull(final Map<String, Object> map, final String key, final Object value) {
        if (value != null) {
            map.put(key, value);
        }
    }
}