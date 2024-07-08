package epams.com.member.service;

import java.util.List;

import epams.com.member.dto.RoleDTO;
import org.springframework.stereotype.Service;

import epams.com.member.dto.IamUserDTO;
import epams.com.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author K140024
 * @implNote 회원 정보 처리를 위한 서비스
 * @since 2024-04-26
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    /**
     * 회원 저장소
     */
    private final MemberRepository memberRepository;

    /**
     * 검색 값을 사용하여 회원을 찾는 메소드
     * 
     * @param searchValue 검색 값
     * @return 검색된 회원 목록
     */
    public List<IamUserDTO> findBySearchValue(final String searchValue) {
        return memberRepository.findBySearchValue(searchValue);
    }

    /**
     * 부서/팀 목록을 리턴하는 메소드
     *
     * @param searchValue 검색 값
     * @return 검색된 회원 목록
     */
    public List<IamUserDTO> findAllDept() {
        return memberRepository.findAllDept();
    }

    /**
     * 모든 사용자를 조회하는 메소드
     * 
     * @since 2024-06-11
     */
    public List<IamUserDTO> findAll() {
        return memberRepository.findAll();
    }
    
    /**
     * 특정 사용자의 권한을 조회하는 메소드
     * 
     * @since 2024-06-11
     */
    public RoleDTO findOneRoleByUsername(final IamUserDTO iamUserDTO) {
        return memberRepository.findOneRoleByUsername(iamUserDTO);
    }

}
