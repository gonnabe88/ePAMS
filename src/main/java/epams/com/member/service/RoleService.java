package epams.com.member.service;

import epams.com.member.dto.RoleDTO;
import epams.com.member.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author K140024
 * @implNote 코드 service
 * @since 2024-04-26
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RoleService {
	
    /**
     * @author K140024
     * @implNote RoleRepository 주입
     * @since 2024-04-26
     */
	private final RoleRepository roleRepository;
	
	/**
     * @author K140024
     * @implNote 전제 목록 조회
     * @since 2024-04-26
     */
    public List<RoleDTO> findAll() {
    	return roleRepository.findAll();
    }
    
    /**
     * @author K140024
     * @implNote 데이터 저장
     * @since 2024-04-26
     */
    public void save(final List<RoleDTO> added, final List<RoleDTO> changed, final List<RoleDTO> deleted) {
        // Handle added members
        for (final RoleDTO dto : added) {
        	roleRepository.insert(dto);
        }

        // Handle changed members
        for (final RoleDTO dto : changed) {
        	roleRepository.update(dto);
        }

        // Handle deleted members
        for (final RoleDTO dto : deleted) {
        	roleRepository.delete(dto);
        }
    }
}
