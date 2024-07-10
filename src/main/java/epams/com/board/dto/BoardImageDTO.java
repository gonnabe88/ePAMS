package epams.com.board.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import epams.com.board.entity.BoardImageEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/***
 * @author 140024
 * @implNote 게시판이미지 테이블 데이터 정의 DTO
 * @since 2024-06-09
 */
@Getter
@Setter
@ToString
@NoArgsConstructor // 기본생성자
@AllArgsConstructor // 모든 필드를 매개변수로 하는 생성자
public class BoardImageDTO extends BaseDTO{
    /***
     * @author 140024
     * @implNote 자동으로 생성되는 auto increment number
     * @since 2024-06-09
     */
    private Long seqId;

    /***
     * @author 140024
     * @implNote 원본 이미지 이름
     * @since 2024-06-09
     */
    private String originalFileName;

    /***
     * @author 140024
     * @implNote 본문
     * @implSpec 저장된 이미지 이름
     * @since 2024-06-09
     */
    private String storedFileName;

    /***
     * @author 140024
     * @implNote 이미지가 첨부된 게시판 ID (작업 예정)
     * @since 2024-06-09
     */
    private Long boardId;

}
