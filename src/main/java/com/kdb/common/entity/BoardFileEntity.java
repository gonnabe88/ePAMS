package com.kdb.common.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/***
 * @author 140024
 * @implNote 게시판 첨부파일 게시판 정의 entity
 * @since 2024-06-09
 */
@Entity
@Getter
@Setter
@Table(name = "board_file")
@NoArgsConstructor // 기본생성자
public class BoardFileEntity extends BaseEntity {
	
    /***
     * @author 140024
     * @implNote 자동으로 생성되는 auto increment number
     * @since 2024-06-10
     */
    @Id
    @Column(name = "SEQ_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seqId;

    /***
     * @author 140024
     * @implNote 파일명
     * @since 2024-06-10
     */
    @Column(name = "ORIGINAL_FILE_NAME")
    private String originalFileName;

    /***
     * @author 140024
     * @implNote 저장파일명 (중복방지)
     * @since 2024-06-10
     */
    @Column(name = "STORED_FILE_NAME")
    private String storedFileName;
    
    /***
     * @author 140024
     * @implNote 저장경로
     * @since 2024-06-10
     */
    @Column(name = "STORED_PATH")
    private String storedPath;

    /***
     * @author 140024
     * @implNote 게시글 ID (외래키 설정 BOARD_FILE.BOARD_ID - BOARD.SEQ_ID)
     * @since 2024-06-10
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_ID")
    private BoardEntity boardEntity;

}