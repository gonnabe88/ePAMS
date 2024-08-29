
package epams.domain.com.sidebar;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
     * @implNote 코드관리 (admin/language.html)
     * @since 2024-06-09
     */
    @GetMapping("/renderSidebarAdmin")
    public String sidebarAdmin(Model model) {
        return "common/layout/sidebarAdmin :: menu";
    }

    /***
     * @author 140024
     * @implNote 화면관리 (admin/page.html)
     * @since 2024-06-09
     */
    @GetMapping("/renderSidebarNormal")
    public String sidebarNormal(Model model) {
        return "common/layout/sidebarNormal :: menu";
    }

}