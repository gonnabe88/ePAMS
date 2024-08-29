package epams.domain.com.sidebar.service;

import epams.domain.com.index.dto.DeptSearchDTO;
import epams.domain.com.index.dto.TeamSearchDTO;
import epams.domain.com.member.dto.IamUserDTO;
import epams.domain.com.member.dto.RoleDTO;
import epams.domain.com.member.repository.MemberRepository;
import epams.domain.com.sidebar.dto.UserInfoDTO;
import epams.domain.com.sidebar.repository.SidebarRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author K140024
 * @implNote 사이드바 기능 구현을 위한 서비스
 * @since 2024-04-26
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SidebarService {

    /**
     * @author 140024
     * @implNote 사이드바 기능 구현을 위한 레파지토리
     * @since 2024-08-29
     */
    private final SidebarRepository sidebarRepository;

    /**
     * @implNote 행번을 사용하여 직원정보 검색
     * @param userNo K/O행번
     * @return 검색된 직원 정보
     */
    public UserInfoDTO findByUserNo(final String userNo) {
        return sidebarRepository.findByUserNo(userNo);
    }


}
