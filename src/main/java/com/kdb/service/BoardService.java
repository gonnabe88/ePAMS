package com.kdb.service;

import com.kdb.dto.BoardDTO;
import com.kdb.dto.BoardFileDTO;
import com.kdb.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public void save(BoardDTO boardDTO) throws IOException {
        if (boardDTO.getBoardFile()==null) {
            // �뙆�씪 �뾾�떎.
            boardDTO.setFileAttached(0);
            boardRepository.save(boardDTO);
        } else {
            // �뙆�씪 �엳�떎.
            boardDTO.setFileAttached(1);
            // 寃뚯떆湲� ���옣 �썑 id媛� �솢�슜�쓣 �쐞�빐 由ы꽩 諛쏆쓬.
            BoardDTO savedBoard = boardRepository.save(boardDTO);
            // �뙆�씪留� �뵲濡� 媛��졇�삤湲�
            for (MultipartFile boardFile: boardDTO.getBoardFile()) {
                // �뙆�씪 �씠由� 媛��졇�삤湲�
                String originalFilename = boardFile.getOriginalFilename();
                System.out.println("originalFilename = " + originalFilename);
                // ���옣�슜 �씠由� 留뚮뱾湲�
                System.out.println(System.currentTimeMillis());
                String storedFileName = System.currentTimeMillis() + "-" + originalFilename;
                System.out.println("storedFileName = " + storedFileName);
                // BoardFileDTO �꽭�똿
                BoardFileDTO boardFileDTO = new BoardFileDTO();
                boardFileDTO.setOriginalFileName(originalFilename);
                boardFileDTO.setStoredFileName(storedFileName);
                boardFileDTO.setBoardId(savedBoard.getId());
                // �뙆�씪 ���옣�슜 �뤃�뜑�뿉 �뙆�씪 ���옣 泥섎━
                String savePath = "/Users/codingrecipe/development/intellij_community/spring_upload_files/" + storedFileName; // mac
//            String savePath = "C:/development/intellij_community/spring_upload_files/" + storedFileName;
                boardFile.transferTo(new File(savePath));
                // board_file_table ���옣 泥섎━
                boardRepository.saveFile(boardFileDTO);
            }
        }
    }

    public List<BoardDTO> findAll() {
        return boardRepository.findAll();
    }

    public void updateHits(Long id) {
        boardRepository.updateHits(id);
    }

    public BoardDTO findById(Long id) {
        return boardRepository.findById(id);
    }

    public void update(BoardDTO boardDTO) {
        boardRepository.update(boardDTO);
    }

    public void delete(Long id) {
        boardRepository.delete(id);
    }

    public List<BoardFileDTO> findFile(Long id) {
        return boardRepository.findFile(id);
    }
}
