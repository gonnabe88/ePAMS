package com.kdb.com.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kdb.com.dto.LogLoginDTO;
import com.kdb.com.repository.LogRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/***
 * @author 140024
 * @implNote 각종 로그 데이터 관리 로직 처리를 위한 service
 * @since 2024-06-09
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LogService {
	
	/***
	 * @author 140024
	 * @implNote logRepository 객체 생성
	 * @since 2024-06-09
	 */
	private final LogRepository logRepository;
    
	/***
	 * @author 140024
	 * @implNote logRepository 객체의 findLoginLogAll 호출하여 LogLoginEntity 리스트 반환
	 * @since 2024-06-09
	 */
    public List<LogLoginDTO> findAll() {    	
    	return logRepository.findAll();
    }  
}