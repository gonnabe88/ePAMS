package epams.com.admin.service;

import java.util.List;

import org.springframework.stereotype.Service;

import epams.com.admin.repository.LoginOtpRepository;
import epams.com.login.dto.LoginOTPDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
	private final LoginOtpRepository loginOtpRepository;
	
	/**
     * @author K140024
     * @implNote 전제 목록 조회
     * @since 2024-04-26
     */
    public List<LoginOTPDTO> findAll() {    	
    	return loginOtpRepository.findAll();
    }
    
    /**
     * @author K140024
     * @implNote 데이터 저장
     * @since 2024-04-26
     */
    public void save(List<LoginOTPDTO> added, List<LoginOTPDTO> changed, List<LoginOTPDTO> deleted) {    	
        // Handle added members
        for (LoginOTPDTO dto : added) {
        	loginOtpRepository.insert(dto);
        }

        // Handle changed members
        for (LoginOTPDTO dto : changed) {
        	loginOtpRepository.update(dto);
        }

        // Handle deleted members
        for (LoginOTPDTO dto : deleted) {
        	loginOtpRepository.delete(dto);
        }
    }
}
