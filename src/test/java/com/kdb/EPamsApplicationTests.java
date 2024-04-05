package com.kdb;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kdb.common.dto.BoardDTO;
import com.kdb.common.service.BoardService;

@SpringBootTest
class EPamsApplicationTests {

    @Autowired
    private BoardService boardService;

    
    public void boardWrite() throws IOException {
        for(int i=1; i<=100; i++) {
            BoardDTO dto = new BoardDTO();
            dto.setBoardTitle("제목"+i);
            dto.setBoardContents("내용"+i);
            dto.setBoardWriter("K140024");
            boardService.save(dto);
        }
    }
    
	@Test
	void contextLoads() {
	}

}
