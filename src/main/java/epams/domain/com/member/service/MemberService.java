package epams.domain.com.member.service;

import java.util.List;

import epams.domain.com.index.dto.DeptSearchDTO;
import epams.domain.com.index.dto.UserSearchDTO;
import epams.domain.com.login.util.MaskPhoneNoUtil;
import epams.domain.com.member.dto.RoleDTO;
import epams.domain.com.member.dto.IamUserDTO;
import epams.domain.com.member.repository.MemberRepository;
import epams.domain.com.sidebar.dto.UserInfoDTO;

import org.springframework.stereotype.Service;

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
     * *
     * @author 140024
     * @implNote 특정 사용자 조직 및 직책 정보 조회
     * @return 부점, 파견부점, 부점지역, 파견부점지역 등
     * @since 2024-10-11
     */
    public UserInfoDTO findUsrDeptInfoByUserNo(final Long empId) {
        return memberRepository.findUsrDeptInfoByUserNo(empId);
    }

    /**
     * *
     * @author 140024
     * @implNote 마스킹된 직원 휴대폰 번호 조회
     * @return 마스킹된 휴대폰 번호
     * @since 2024-09-05
     */
    public IamUserDTO findUserByUserNo(final String username) {
        return memberRepository.findUserByUserNo(username);
    }

    /**
     * *
     * @author 140024
     * @implNote 마스킹된 직원 휴대폰 번호 조회
     * @return 마스킹된 휴대폰 번호
     * @since 2024-09-05
     */
    public String findMaskedPhoneNo(final Long username) {
        String phoneNo = memberRepository.findPhoneNo(username);        
        return MaskPhoneNoUtil.maskPhoneNo(phoneNo);
    }

    /**
     * *
     * @author 140024
     * @implNote 직원 휴대폰 번호 조회
     * @return 휴대폰 번호
     * @since 2024-09-05
     */
    public String findMPhoneNo(final Long username) {    
        return memberRepository.findPhoneNo(username); 
    }

    /**
     * 검색 값을 사용하여 회원을 찾는 메소드
     * 
     * @param searchValue 검색 값
     * @return 검색된 회원 목록
     */
    public List<IamUserDTO> findBySearchValue(final String searchValue) {
        List<IamUserDTO> iamUserDTOList = memberRepository.findBySearchValue(searchValue);
        for (IamUserDTO iamUserDTO : iamUserDTOList) {
            log.warn(iamUserDTO.toString());
            iamUserDTO.formatContactNumber(iamUserDTO.getPhoneNo(), iamUserDTO.getInlineNo(), iamUserDTO.getStartTime(), iamUserDTO.getEndTime(),"KR");
            log.warn(iamUserDTO.toString());
        }
        return iamUserDTOList;
    }

    /**
     * 부서/팀 목록을 리턴하는 메소드
     *
     * @param searchValue 검색 값
     * @return 검색된 회원 목록
     */
    public List<DeptSearchDTO> findAllDept() {
        return memberRepository.findAllDept();
    }

    /**
     * 부서/팀 목록을 리턴하는 메소드
     *
     * @param searchValue 검색 값
     * @return 검색된 회원 목록
     */
    public List<UserSearchDTO> findAllUser() {
        return memberRepository.findAllUser();
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
    public List<RoleDTO> findOneRoleByUsername(final IamUserDTO iamUserDTO) {
        return memberRepository.findOneRoleByUsername(iamUserDTO);
    }

}
