package com.kdb.common.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.kdb.common.dto.BoardDTO;
import com.kdb.common.entity.BoardEntity;
import com.kdb.common.entity.BoardFileEntity;
import com.kdb.common.repository.BoardFileRepository;
import com.kdb.common.repository.BoardRepository;
import com.kdb.common.repository.BoardRepository2;
import com.kdb.common.dto.BoardFileDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// DTO -> Entity (Entity Class)
// Entity -> DTO (DTO Class)

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardRepository2 boardRepository2;

    private final BoardFileRepository boardFileRepository;
    

    public BoardDTO update(BoardDTO boardDTO) throws IOException {
        if (boardDTO.getBoardFile()==null) {
            // 첨부 파일 없음.
	        BoardEntity boardEntity = BoardEntity.toUpdateEntity(boardDTO);
	        boardRepository2.update(boardEntity);
        }
        else {
            BoardEntity boardEntity = BoardEntity.toUpdateFileEntity(boardDTO);
            Long savedId = boardRepository.save(boardEntity).getId();
            BoardEntity board = boardRepository.findById(savedId).get();
            
        	for (MultipartFile boardFile: boardDTO.getBoardFile()) {        		
	            String originalFilename = boardFile.getOriginalFilename(); // 2.
	            String storedFileName = System.currentTimeMillis() + "_" + originalFilename; // 3.
	            String savePath = "C:/epams/" + storedFileName; // 파일 저장경로 및 이름
	            boardFile.transferTo(new File(savePath)); // 5.
	            BoardFileEntity boardFileEntity = BoardFileEntity.toBoardFileEntity(board, originalFilename, storedFileName);
	            boardFileRepository.save(boardFileEntity);
        	}
        }
        return findById(boardDTO.getId());
    }

    public void save(BoardDTO boardDTO) throws IOException {
        // 파일 첨부 여부에 따라 로직 분리
        if (boardDTO.getBoardFile()==null) {
            // 첨부 파일 없음
            BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDTO);
            //System.out.println("[service] boardDTO = " + boardDTO);
            //System.out.println("[service] boardEntity = " + boardEntity.getBoardWriter());
            boardRepository.save(boardEntity);
        } else {
        	
            BoardEntity boardEntity = BoardEntity.toSaveFileEntity(boardDTO);
            Long savedId = boardRepository.save(boardEntity).getId();
            BoardEntity board = boardRepository.findById(savedId).get();
        	
        	for (MultipartFile boardFile: boardDTO.getBoardFile()) {        		
	            String originalFilename = boardFile.getOriginalFilename(); // 2.
	            String storedFileName = System.currentTimeMillis() + "_" + originalFilename; // 3.
	            String savePath = "C:/epams/" + storedFileName; // 파일 저장경로 및 이름
	            boardFile.transferTo(new File(savePath)); // 5.
	
	            BoardFileEntity boardFileEntity = BoardFileEntity.toBoardFileEntity(board, originalFilename, storedFileName);
	            boardFileRepository.save(boardFileEntity);
        	}
        }
    }
    
    public void delete(Long id) {    	
    	BoardDTO boardDTO = boardRepository2.findById(id);
    	List<BoardFileDTO> boardFiles = boardRepository2.findFile(id);
    	
    	// 첨부파일이 없는 경우 DB만 삭제
    	if (boardDTO.getFileAttached()==0) {
    		log.warn("No files");
    		boardRepository.deleteById(id);
    	}
    	// 첨부파일이 있는 경우 File도 삭제
    	else {
    		boardRepository.deleteById(id);
    		for (BoardFileDTO boardFile: boardFiles) {        
    			File file = new File("C:/epams/" + boardFile.getStoredFileName());
    			boolean result = file.delete();
    			if (log.isWarnEnabled()) 
    				log.warn(result + " Delete files : "+ boardFile.getStoredFileName());
        	}
    	}
    }
    

    @Transactional
    public List<BoardDTO> findAll() {
        List<BoardEntity> boardEntityList = boardRepository.findAll();
        List<BoardDTO> boardDTOList = new ArrayList<>();
        for (BoardEntity boardEntity: boardEntityList) {
            boardDTOList.add(BoardDTO.toBoardDTO(boardEntity));
        }
        return boardDTOList;
    }

    @Transactional
    public void updateHits(Long id) {
        boardRepository.updateHits(id);
    }

    @Transactional
    public BoardDTO findById(Long id) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);
        if (optionalBoardEntity.isPresent()) {
            BoardEntity boardEntity = optionalBoardEntity.get();
            BoardDTO boardDTO = BoardDTO.toBoardDTO(boardEntity);
            return boardDTO;
        } else {
            return null;
        }
    }



    public Page<BoardDTO> paging(Pageable pageable) {
        int page = pageable.getPageNumber() - 1;
        int pageLimit = 3; // 한 페이지에 보여줄 글 갯수
        // 한페이지당 3개씩 글을 보여주고 정렬 기준은 id 기준으로 내림차순 정렬
        // page 위치에 있는 값은 0부터 시작
        Page<BoardEntity> boardEntities =
                boardRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));

       // System.out.println("boardEntities.getContent() = " + boardEntities.getContent()); // 요청 페이지에 해당하는 글
       // System.out.println("boardEntities.getTotalElements() = " + boardEntities.getTotalElements()); // 전체 글갯수
      //  System.out.println("boardEntities.getNumber() = " + boardEntities.getNumber()); // DB로 요청한 페이지 번호
       // System.out.println("boardEntities.getTotalPages() = " + boardEntities.getTotalPages()); // 전체 페이지 갯수
      //  System.out.println("boardEntities.getSize() = " + boardEntities.getSize()); // 한 페이지에 보여지는 글 갯수
//        System.out.println("boardEntities.hasPrevious() = " + boardEntities.hasPrevious()); // 이전 페이지 존재 여부
//        System.out.println("boardEntities.isFirst() = " + boardEntities.isFirst()); // 첫 페이지 여부
//        System.out.println("boardEntities.isLast() = " + boardEntities.isLast()); // 마지막 페이지 여부

        // 목록: id, writer, title, hits, createdTime
        Page<BoardDTO> boardDTOS = boardEntities.map(
        		board -> new BoardDTO(
        				board.getId(), 
        				board.getBoardWriter(), 
        				board.getBoardContents() , 
        				board.getBoardTitle(),         				
        				board.getBoardHits(), 
        				board.getCreatedTime()));
        

        
        return boardDTOS;
    }
    
    public BoardFileDTO findOneFile(Long id) {
        return boardRepository2.findByFileId(id);
    }
    public List<BoardFileDTO> findFile(Long id) {
        return boardRepository2.findFile(id);
    }
    
    
}