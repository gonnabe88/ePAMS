package epams.com.admin.entity;

import java.time.LocalDateTime;

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
 * @implNote 코드 테이블 정의 entity
 * @since 2024-06-09
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "COM_VIEW_LOG")
public class LogViewEntity {
	
    /***
     * @author 140024
     * @implNote ID
     * @since 2024-06-09
     */
    @Id // 기본키
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // auto_increment
    @Column(name = "SEQ_ID")
    private Long SEQ_ID;
    
    /***
     * @author 140024
     * @implNote 호출 컨트롤러명
     * @since 2024-06-09
     */
    @Column(name = "CTRL_NM", length = 100, nullable = false)
    private String CTRL_NM;

    /***
     * @author 140024
     * @implNote 호출 메소드명
     * @since 2024-06-09
     */
    @Column(name = "MTHD_NM", length = 20, nullable = false) 
    private String MTHD_NM;
    
    /***
     * @author 140024
     * @implNote 접속 IP
     * @since 2024-06-09
     */
    @Column(name = "CLIENT_IP", length = 15, nullable = false) 
    private String CLIENT_IP;
    
    /***
     * @author 140024
     * @implNote 단말정보
     * @since 2024-06-09
     */
    @Column(name = "USER_AGENT", length = 500, nullable = false) 
    private String USER_AGENT;
    
    /***
     * @author 140024
     * @implNote 요청 URL
     * @since 2024-06-09
     */
    @Column(name = "RQST_URL", length = 500, nullable = false) 
    private String RQST_URL;
    
    /***
     * @author 140024
     * @implNote 호출시간
     * @since 2024-06-09
     */
    @Column(name = "CALL_TIME", updatable = false)
    private LocalDateTime CALL_TIME;
    
    
    
}