package epams.com.member.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import epams.com.member.entity.TempUserEntity;
import epams.com.member.repository.MemberJpaRepository;
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
public class MemberDetailsService implements UserDetailsService {

    /**
     * 회원 저장소
     */
    private final MemberJpaRepository memberRepository;

    /**
     * 회원 정보를 저장하는 메소드
     * 
     * @param added   추가된 회원 목록
     * @param changed 변경된 회원 목록
     * @param deleted 삭제된 회원 목록
     */
    public void saveMembers(final List<TempUserEntity> added, final List<TempUserEntity> changed, final List<TempUserEntity> deleted) {

        // Handle added members
        for (final TempUserEntity newMember : added) {
            memberRepository.save(newMember);
        }

        // Handle changed members
        for (final TempUserEntity changedMember : changed) {
            final Optional<TempUserEntity> memberOpt = memberRepository.findByUsername(changedMember.getUsername());
            memberOpt.ifPresent(member -> {
                member.setUsername(changedMember.getUsername());
                member.setDept(changedMember.getDept());
                member.setResponsibility(changedMember.getResponsibility());
                member.setRole(changedMember.getRole());
                member.setTeam(changedMember.getTeam());
                memberRepository.save(member);
            });
        }

        // Handle deleted members
        for (final TempUserEntity deletedMember : deleted) {
            final Optional<TempUserEntity> memberOpt = memberRepository.findByUsername(deletedMember.getUsername());
            memberOpt.ifPresent(memberRepository::delete);
        }
    }

    /**
     * 사용자 이름으로 사용자를 로드하는 메소드
     * 
     * @param username 사용자 이름
     * @return UserDetails 객체
     * @throws UsernameNotFoundException 사용자 이름이 존재하지 않을 때 발생하는 예외
     */
    @Override
    public UserDetails loadUserByUsername(final String username) {
        final Optional<TempUserEntity> findMember = memberRepository.findByUsername(username);

        if (findMember.isEmpty()) {
            throw new UsernameNotFoundException("존재하지 않는 username 입니다." + username);
        }

        final TempUserEntity member = findMember.get();

        return new User(member.getUsername(), member.getPassword(), AuthorityUtils.createAuthorityList(member.getRole().toString()));
    }

    /**
     * 모든 회원을 찾는 메소드
     * 
     * @return 모든 회원 목록
     */
    public List<TempUserEntity> findAll() {
        return memberRepository.findAll();
    }
}
