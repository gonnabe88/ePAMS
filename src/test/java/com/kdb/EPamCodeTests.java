package com.kdb;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kdb.com.entity.CodeEntity;
import com.kdb.com.repository.CodeRepository;

@SpringBootTest
class EPamCodeTests {

    @Autowired
    private CodeRepository codeRepository;

    @Test
    public void RegistOneMember() throws IOException {    	
        
    	CodeEntity code = CodeEntity.builder()
            .CD("COM_INDEX_CD")
            .CD_NM("빠른 신청")
            .CD_TYPE("text")
            .build();
    	codeRepository.save(code);
    	
    	CodeEntity code2 = CodeEntity.builder()
            .CD("COM_BANNER1_CD")
            .CD_NM("모바일 근태 시스템")
            .CD_TYPE("text")
            .build();
    	codeRepository.save(code2);
        	
    	CodeEntity code3 = CodeEntity.builder()
            .CD("COM_BANNERCONTENT1_CD")
            .CD_NM("ePAMS에 오신걸<br>진심으로 환영합니다")
            .CD_TYPE("html")
            .build();
    	codeRepository.save(code3);
    	
    	CodeEntity code4 = CodeEntity.builder()
            .CD("DTM_INDEX_CD")
            .CD_NM("근태신청")
            .CD_TYPE("text")
            .build();
    	codeRepository.save(code4);  
            
    }

}
