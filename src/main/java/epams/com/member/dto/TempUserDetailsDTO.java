package epams.com.member.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import epams.com.member.entity.TempUserEntity;
import lombok.RequiredArgsConstructor;

/**
 * @author K140024
 * @implNote 회원의 세부 정보를 나타내는 DTO 클래스
 * @since 2024-06-11
 */
@RequiredArgsConstructor
public class TempUserDetailsDTO implements UserDetails {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -8447696968169312646L;

    /**
     * TempUserEntity 인스턴스
     */
    private final TempUserEntity member;

    /**
     * 권한을 반환하는 메소드
     * 
     * @return 권한의 컬렉션
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        final List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + member.getRole().name()));
        return authorities;
    }

    /**
     * 비밀번호를 반환하는 메소드
     * 
     * @return 비밀번호
     */
    @Override
    public String getPassword() {
        return member.getPassword();
    }

    /**
     * 사용자 이름을 반환하는 메소드
     * 
     * @return 사용자 이름
     */
    @Override
    public String getUsername() {
        return member.getUsername();
    }

    /**
     * 계정이 만료되지 않았는지 확인하는 메소드
     * 
     * @return true 항상 만료되지 않음
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 계정이 잠기지 않았는지 확인하는 메소드
     * 
     * @return true 항상 잠기지 않음
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 자격 증명이 만료되지 않았는지 확인하는 메소드
     * 
     * @return true 항상 만료되지 않음
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 계정이 활성화되어 있는지 확인하는 메소드
     * 
     * @return true 항상 활성화됨
     */
    @Override
    public boolean isEnabled() {
        return true;
    }    
}
