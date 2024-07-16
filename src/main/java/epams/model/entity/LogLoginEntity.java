package epams.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

/***
 * @author 140024
 * @implNote Login Log Table을 객체로 관리하기 위한 entity
 * @since 2024-06-09
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "THURXE_CLOGNL")
@Comment("인사_외부근태 로그인로그")
public class LogLoginEntity extends BaseEntity {

    /**
     * @author 140024
     * @implNote BLB_SNO 시퀀스 이름 상수
     * @since 2024-06-09
     */
    private static final String SEQUENCE = "LGN_LOG_SNO";

    /***
     * @author 140024
     * @implNote 자동으로 생성되는 auto increment number
     * @since 2024-06-09
     */
    @Id // 기본키
    @Column(name = SEQUENCE, columnDefinition = "NUMBER(22)")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE)
    @SequenceGenerator(name = SEQUENCE, sequenceName = SEQUENCE, allocationSize = 1)
    @Comment("로그인로그일련번호")
    private Long LGN_LOG_SNO;
    
    /***
     * @author 140024
     * @implNote 행번 
     * @since 2024-06-09
     */
    @Column(name = "ENO", nullable = false, length = 32)
    @Comment("사원번호")
    private String ENO;
    
    /***
     * @author 140024
     * @implNote 인증방식
     * @since 2024-06-09
     */
    @Column(name = "CER_KD_NM", nullable = false, length = 200)
    @Comment("로그인종류명")
    private String CER_KD_NM;
    
    /***
     * @author 140024
     * @implNote 성공여부
     * @since 2024-06-09
     */
    @Column(name = "LGN_SCS_YN", nullable = false, length = 1)
    @Comment("로그인성공여부")
    private char LGN_SCS_YN;

}