package com.kdb.common.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.kdb.common.entity.MemberEntity;

public class MemberDetailsDTO implements UserDetails {

	private MemberEntity member;
	
	public String getUUID() {
		return member.getUuid();
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + member.getRole().name()));
        return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return member.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return member.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}    
}
