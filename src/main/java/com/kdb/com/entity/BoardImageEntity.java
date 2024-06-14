package com.kdb.com.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/***
 * @author 140024
 * @implNote 게시판 이미지 Table을 객체로 관리하기 위한 entity
 * @since 2024-06-09
 */
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Table(name = "board_image")
public class BoardImageEntity extends BaseEntity {
	
    /***
     * @author 140024
     * @implNote 자동으로 생성되는 auto increment number
     * @since 2024-06-09
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seqId")
    private Long seqId;

    /***
     * @author 140024
     * @implNote 원본 파일명
     * @since 2024-06-09
     */
    @Column
    private String originalFileName;
    
    /***
     * @author 140024
     * @implNote 저장파일명 (중복방지)
     * @since 2024-06-10
     */
    @Column
    private String storedFileName;

    
    /***
     * @author 140024
     * @implNote 게시글 ID (외래키 설정 BOARD_IMAGE.BOARDID - BOARD.SEQID)
     * @since 2024-06-10
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardId")
    private BoardEntity boardEntity;

    /***
     * @author 140024
     * @implNote 파일 정보를 BoardImageEntity로 변환
     * @since 2024-06-10
     */
    public static BoardImageEntity toBoardFileEntity(final String originalFileName, final String storedFileName) {
        final BoardImageEntity boardFileEntity = new BoardImageEntity();
        boardFileEntity.setOriginalFileName(originalFileName);
        boardFileEntity.setStoredFileName(storedFileName);
        return boardFileEntity;
    }

}