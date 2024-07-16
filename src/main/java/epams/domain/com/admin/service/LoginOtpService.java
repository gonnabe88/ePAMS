package epams.domain.com.admin.service;

import epams.domain.com.admin.repository.LoginOtpRepository;
import epams.domain.com.login.dto.LoginOTPDTO;
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
public class LoginOtpService {
	
    /**
     * @author K140024
     * @implNote CodeRepository 주입
     * @since 2024-04-26
     */
	private final LoginOtpRepository loginOtpRepo;
	
	/**
     * @author K140024
     * @implNote 전제 목록 조회
     * @since 2024-04-26
     */
    public List<LoginOTPDTO> findAll() {    	
    	return loginOtpRepo.findAll();
    }
    
    /**
     * @author K140024
     * @implNote 데이터 저장
     * @since 2024-04-26
     */
    public void save(final List<LoginOTPDTO> added, final List<LoginOTPDTO> changed, final List<LoginOTPDTO> deleted) {    	
        // Handle added members
        for (final LoginOTPDTO dto : added) {
        	loginOtpRepo.insert(dto);
        }

        // Handle changed members
        for (final LoginOTPDTO dto : changed) {
        	loginOtpRepo.update(dto);
        }

        // Handle deleted members
        for (final LoginOTPDTO dto : deleted) {
        	loginOtpRepo.delete(dto);
        }

    }
}
