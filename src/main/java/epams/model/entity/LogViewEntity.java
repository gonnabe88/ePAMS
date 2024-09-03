package epams.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

/***
 * @author 140024
 * @implNote 뷰페이지 로그 테이블 정의 entity
 * @since 2024-06-09
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@Builder
@Table(name = "THURXE_CVIEWL")
@Comment("인사_외부근태 페이지뷰로그")
public class LogViewEntity extends BaseEntity {

    /**
     * @author 140024
     * @implNote BLB_SNO 시퀀스 이름 상수
     * @since 2024-06-09
     */
    private static final String SEQUENCE = "SQ_THURXE_CVIEWL_1";

    /***
     * @author 140024
     * @implNote ID
     * @since 2024-06-09
     */
    @Id // 기본키
    @Column(name = "PAG_LOG_SNO", columnDefinition = "NUMBER(22)")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE)
    @SequenceGenerator(name = SEQUENCE, sequenceName = SEQUENCE, allocationSize = 1)
    @Comment("페이지로그일련번호")
    private Long PAG_LOG_SNO;
    
    /***
     * @author 140024
     * @implNote 호출 컨트롤러명
     * @since 2024-06-09
     */
    @Column(name = "CTRL_NM", length = 500, nullable = false)
    @Comment("컨트롤러명")
    private String CTRL_NM;

    /***
     * @author 140024
     * @implNote 호출 메소드명
     * @since 2024-06-09
     */
    @Column(name = "MTH_NM", length = 100, nullable = false) 
    @Comment("메소드명")
    private String MTH_NM;
    
    /***
     * @author 140024
     * @implNote 접속 IP
     * @since 2024-06-09
     */
    @Column(name = "CLI_IP_ADDR", length = 300, nullable = false) 
    @Comment("클라이언트IP주소")
    private String CLI_IP_ADDR;
    
    /***
     * @author 140024
     * @implNote 사용자접속환경내용
     * @since 2024-06-09
     */
    @Column(name = "USR_CNC_ENV_CONE", length = 2000, nullable = false)
    @Comment("사용자접속환경내용")
    private String USR_CNC_ENV_CONE;
    
    /***
     * @author 140024
     * @implNote 요청 URL
     * @since 2024-06-09
     */
    @Column(name = "REQ_URL_ADDR", length = 500, nullable = false) 
    @Comment("요청URL주소")
    private String REQ_URL_ADDR;
    
}