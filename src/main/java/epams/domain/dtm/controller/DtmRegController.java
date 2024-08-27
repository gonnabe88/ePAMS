package epams.domain.dtm.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import epams.domain.com.admin.service.HtmlLangDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * @author K210058
 * @implNote 근태신청 컨트롤러
 * @since 2024-08-27
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/dtm")
public class DtmRegController<S extends Session> {

    /**
     * @author K210058
     * @implNote 근태신청 컨트롤러
     * @since 2024-08-27
     */
    private final HtmlLangDetailService langDetailService;
    private static final Logger logger = LoggerFactory.getLogger(DtmRegController.class);

    /**
     * @author K210058
     * @implNote 근태신청 컨트롤러
     * @since 2024-08-27
     */
    @GetMapping("/dtmReg")
    public String dtmReg(@PageableDefault(page = 1) final Pageable pageable, final Model model) {
        final String DTMREG = "dtm/dtmReg";

        // 언어목록
        final Map<String, String> langList = langDetailService.getCodeHtmlDetail(DTMREG);
        model.addAttribute("langList", langList);

        return DTMREG;
    }

    /**
     * @author K210058
     * @implNote 근태신청 컨트롤러
     * @since 2024-08-27
     */
    @GetMapping("/dtmRegDetail")
    public String dtmRegDetail(@RequestParam("date") String date, Model model) {
        final String DTMREGDET = "dtm/dtmRegDetail";

        // 언어목록
        final Map<String, String> langList = langDetailService.getCodeHtmlDetail(DTMREGDET);
        model.addAttribute("langList", langList);

        if (date != null) {
            model.addAttribute("selectedDate", date);
        }

        return DTMREGDET;
    }

}
