package epams.domain.com.popup;

import epams.domain.com.admin.service.HtmlLangDetailService;
import epams.domain.com.board.dto.BoardDTO;
import epams.domain.com.board.service.BoardMainService;
import epams.domain.com.index.dto.BannerDTO;
import epams.domain.com.index.dto.QuickApplDTO;
import epams.domain.com.login.util.webauthn.authenticator.Authenticator;
import epams.domain.com.login.util.webauthn.service.RegistrationService;
import epams.domain.com.login.util.webauthn.user.AppUser;
import epams.domain.com.member.dto.IamUserDTO;
import epams.domain.com.member.service.MemberService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author K140024
 * @implNote 메인화면(index) Controller
 * @since 2024-06-20
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class toastController<S extends Session> {

    /**
     * @author K140024
     * @implNote 현재 인증된 사용자 정보를 반환하는 메서드
     * @since 2024-06-11
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
     * @implNote 팝업 화면을 반환하는 메서드
     * @since 2024-06-11
     */
    @GetMapping("/toast")
    public String toastPopup() {
        return "common/toastAlert";
    }

}
