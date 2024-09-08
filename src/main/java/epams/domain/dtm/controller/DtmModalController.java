package epams.domain.dtm.controller;

import epams.domain.dtm.dto.DtmHisDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author K140024
 * @implNote 근태 모달화면 컨트롤러
 * @since 2024-06-11
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/dtm")
public class DtmModalController {

    @GetMapping("/dtmApplProcessModal")
    public String dtmApplProcessModal() {
        return "dtm/dtmApplProcessModal";
    }
}
