package epams.com.member.controller;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import epams.com.member.entity.SearchMemberEntity;
import epams.com.member.service.MemberDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author K140024
 * @implNote 회원 관련 처리를 위한 Rest 컨트롤러
 * @since 2024-06-11
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/")
public class MemberRestController {

    /**
     * @author K140024
     * @implNote MemberDetailsService 주입
     * @since 2024-06-11
     */
    private final MemberDetailsService memberservice;

    /**
     * @author K140024
     * @implNote 회원 검색을 처리하는 메서드
     * @since 2024-06-11
     */
    @GetMapping("/search")
    public List<SearchMemberEntity> search(final Model model, @RequestParam("text") final String text) throws Exception {
        final List<SearchMemberEntity> memberList = memberservice.findBySearchValue(text);
        return memberList;
    }

}
