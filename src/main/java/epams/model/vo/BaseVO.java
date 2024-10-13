package epams.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

/***
 * @author 140024
 * @implNote 게시판 테이블 데이터 정의 DTO
 * @since 2024-06-09
 */
@ToString(callSuper = true)
@Data
public class BaseVO {

    /***
     * @author 140024
     * @implNote [Column] 삭제여부(시스템 공통 컬럼)
     * @since 2024-06-09
     */
    private String deleteYn;

    /***
     * @author 140024
     * @implNote [Column] GUID 거래ID(시스템 공통 컬럼 - Nexcore)
     * @since 2024-06-09
     */
    private String guid;

    /***
     * @author 140024
     * @implNote [Column] GUID 거래ID 진행 일련번호(시스템 공통 컬럼 - Nexcore)
     * @since 2024-06-09
     */
    private Integer guidPrgSno;

    /***
     * @author 140024
     * @implNote [Column] 최종변경사용자ID(시스템 공통 컬럼)
     * @since 2024-06-09
     */
    private String lstChgUsid;

    /***
     * @author 140024
     * @implNote [Column] 수정일시(AMN_DTM)
     * @since 2024-06-09
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedTime;

    /***
     * @author 140024
     * @implNote [Column] 생성일시(FST_ENR_DTM)
     * @since 2024-06-09
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdTime;

    /***
     * @author 140024
     * @implNote [Column] 생성사용자ID(시스템 공통 컬럼)
     * @since 2024-06-09
     */
    private String createUsid;

    // 기본 생성자
    public BaseVO() {
        this.deleteYn = "N";
        this.guid = new String(new char[38]).replace('\0', '0');
        this.guidPrgSno  = 0;
        this.lstChgUsid = "SYSTEM";
        this.createUsid = "SYSTEM";
        this.updatedTime = LocalDateTime.now();  // 현재 시간으로 초기화
        this.createdTime = LocalDateTime.now();  // 현재 시간으로 초기화
    }

    // 모든 필드를 매개변수로 하는 생성자
    public BaseVO(String deleteYn, String guid, int guidPrgSno, String lstChgUsid, String createUsid, LocalDateTime updatedTime, LocalDateTime createdTime) {
        this.deleteYn = deleteYn;
        this.guid = guid;
        this.guidPrgSno = guidPrgSno;
        this.lstChgUsid = lstChgUsid;
        this.createUsid = createUsid;
        this.updatedTime = updatedTime != null ? updatedTime : LocalDateTime.now();  // 현재 시간으로 초기화
        this.createdTime = createdTime != null ? createdTime : LocalDateTime.now();  // 현재 시간으로 초기화
    }

}