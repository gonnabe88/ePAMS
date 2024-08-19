
package epams.domain.com.sidebar;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.info.InfoEndpoint;
import org.springframework.boot.actuate.logging.LoggersEndpoint;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/***
 * @author 140024
 * @implNote 관리자용 화면 controller
 * @since 2024-06-09
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/common/layout")
public class SidebarController {


    /***
     * @author 140024
     * @implNote 코드관리 (admin/code.html)
     * @since 2024-06-09
     */
    @GetMapping("/renderSidebarAdmin")
    public String sidebarAdmin(Model model) {
        log.warn("sidebarAdmin");
        return "/common/layout/sidebarAdmin :: menu";
    }

    /***
     * @author 140024
     * @implNote 화면관리 (admin/page.html)
     * @since 2024-06-09
     */
    @GetMapping("/renderSidebarNormal")
    public String sidebarNormal(Model model) {
        log.warn("sidebarNormal");
        return "/common/layout/sidebarNormal :: menu";
    }

}