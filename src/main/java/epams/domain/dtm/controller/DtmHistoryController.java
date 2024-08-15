package epams.domain.dtm.controller;

import epams.domain.com.admin.service.CodeHtmlDetailService;
import epams.domain.com.commonCode.CommonCodeService;
import epams.domain.dtm.dto.DtmHisDTO;
import epams.domain.dtm.service.DtmHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author K140024
 * @implNote 근태관리 컨트롤러
 * @since 2024-06-11
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/dtm")
public class DtmHistoryController<S extends Session> {

    /**
     * @author K140024 kk
     * @implNote list 화면에 노출할 게시물 수 설정값 (application.yml)
     * @since 2024-06-11
     */
    @Value("${kdb.listBrdCnt}")
    private int listBrdCnt;

    /**
     * @author K140024
     * @implNote 모든 페이지네이션 시 노출할 최대 버튼 수 설정값 (application.yml)
     * @since 2024-06-11
     */
    @Value("${kdb.maxPageBtn}")
    private int maxPageBtn;

    /**
     * @author K140024
     * @implNote 코드 상세 서비스 주입
     * @since 2024-06-11
     */
    private final CodeHtmlDetailService codeDetailService;

    /**
     * @author K140024
     * @implNote DTM 서비스 주입
     * @since 2024-06-11
     */
    private final DtmHistoryService dtmHisService;


    /**
     * @author K140024
     * @implNote 현재 인증된 사용자 정보를 가져오는 메소드
     * @since 2024-04-26
     */
    private Authentication authentication() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Authentication result;
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            result = null;
        } else {
            result = authentication;
        }
        return result;
    }

    /**
     * @author K140024
     * @implNote DTM 리스트 화면을 반환하는 메서드
     * @since 2024-06-11
     */
    @GetMapping("/list")
    public String dtmList(@PageableDefault(page = 1) final Pageable pageable, @ModelAttribute final DtmHisDTO dto, final Model model) {
        final String DTMLIST = "/dtm/list";
        dto.setEmpId(Long.parseLong(authentication().getName().replace('K', '7')));

        // 코드
        final Map<String, String> codeList = codeDetailService.getCodeHtmlDetail(DTMLIST);
        model.addAttribute("codeList", codeList);

        // 근태목록 출력
        final int currentPage = pageable.getPageNumber();
        final Pageable updatedPageable = PageRequest.of(currentPage, dto.getItemsPerPage());
        final Page<DtmHisDTO> dtos = dtmHisService.findByCondition(updatedPageable, dto);
        final int totalPages = dtos.getTotalPages();
        int startPage = Math.max(1, currentPage - (maxPageBtn / 2));
        final int endPage = Math.min(totalPages, startPage + maxPageBtn - 1);

        if (endPage - startPage < maxPageBtn - 1) {
            startPage = Math.max(1, endPage - maxPageBtn + 1);
        }


        log.warn(dtos.toString());
        //log.info("startPage : " + startPage + " endPage : " + endPage + " totalPages : " + totalPages + " currentPage : " + currentPage);
        model.addAttribute("dtmHis", dtos);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("itemsPerPage", dto.getItemsPerPage());

        return DTMLIST;
    }
}
