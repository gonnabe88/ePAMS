package epams.domain.dtm.controller;

import epams.domain.com.commonCode.CommonCodeService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    private final CommonCodeService commonCodeService;

    /**
     * @author K210058
     * @implNote 근태신청 컨트롤러
     * @since 2024-08-27
     */
    @GetMapping("/dtmReg")
    public String dtmReg(@PageableDefault(page = 1) final Pageable pageable, final Model model) {
        final String DTMREG = "dtmCal";

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
    public String dtmRegDetail(Model model) {
        final String DTMREGDET = "dtm/dtmRegDetail";

        // 언어목록
        final Map<String, String> langList = langDetailService.getCodeHtmlDetail(DTMREGDET);
        model.addAttribute("langList", langList);


        return DTMREGDET;
    }

    @PostMapping("/submitDtmReg")
    public String submitDtmReg(@RequestParam("startDate") String startDate,
                               @RequestParam("endDate") String endDate,
                               @RequestParam("dtmReasonCd") String dtmReasonCd,
                               Model model) {

        final String DTMREGDET = "dtm/dtmApplPopup";

        // 근태 사유코드명 조회
        String codeName = commonCodeService.getCodeName("DTM_KIND", dtmReasonCd);

        // 파라미터를 모델에 추가하여 팝업으로 전달할 데이터 구성
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("dtmKind", "DTM01");
        model.addAttribute("dtmReasonCd", dtmReasonCd);
        model.addAttribute("dtmReasonCdName", codeName);

        return DTMREGDET;
    }

}
