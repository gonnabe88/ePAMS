package epams.com.admin.entity;

import java.time.LocalDate;

import org.hibernate.annotations.Comment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/***
 * @author 140024
 * @implNote 뷰페이지 로그 테이블 정의 entity
 * @since 2024-06-09
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "THURXE_CVIEWL")
@Comment("인사_외부근태 페이지뷰로그")
public class LogViewEntity {
	
    /***
     * @author 140024
     * @implNote ID
     * @since 2024-06-09
     */
    @Id // 기본키
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // auto_increment
    @Column(name = "PAG_LOG_SNO", columnDefinition = "NUMBER(22)")
    @Comment("페이지뷰로그일련번호")
    private Long SEQ_ID;
    
    /***
     * @author 140024
     * @implNote 호출 컨트롤러명
     * @since 2024-06-09
     */
    @Column(name = "CTRL_NM", length = 100, nullable = false)
    @Comment("컨트롤러명")
    private String CTRL_NM;

    /***
     * @author 140024
     * @implNote 호출 메소드명
     * @since 2024-06-09
     */
    @Column(name = "MTH_NM", length = 100, nullable = false) 
    @Comment("메소드명")
    private String MTHD_NM;
    
    /***
     * @author 140024
     * @implNote 접속 IP
     * @since 2024-06-09
     */
    @Column(name = "CLI_IP_ADDR", length = 300, nullable = false) 
    @Comment("클라이언트IP주소")
    private String CLIENT_IP;
    
    /***
     * @author 140024
     * @implNote 단말정보
     * @since 2024-06-09
     */
    @Column(name = "USR_CNC_ENV_INF", length = 2000, nullable = false) 
    @Comment("USER AGENT")
    private String USER_AGENT;
    
    /***
     * @author 140024
     * @implNote 요청 URL
     * @since 2024-06-09
     */
    @Column(name = "REQ_URL_ADDR", length = 500, nullable = false) 
    @Comment("요청 URL주소")
    private String RQST_URL;
    
    /***
     * @author 140024
     * @implNote 호출시간
     * @since 2024-06-09
     */
    @Column(name = "CALL_DTM", updatable = false, columnDefinition = "DATE")
    @Comment("호출시간")
    private LocalDate CALL_TIME;
    
    
    
}