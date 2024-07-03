package epams.com.admin.entity;

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
import java.time.LocalDate;
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
public class LogLoginEntity {

    /***
     * @author 140024
     * @implNote 자동으로 생성되는 auto increment number
     * @since 2024-06-09
     */
    @Id // 기본키
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    @Column(name = "LGN_LOG_SNO", columnDefinition = "NUMBER(22)")
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
    @Column(name = "LGN_KD_NM", nullable = false, length = 100)
    @Comment("로그인종류명")
    private String LGN_KD_NM;
    
    /***
     * @author 140024
     * @implNote 성공여부
     * @since 2024-06-09
     */
    @Column(name = "LGN_SCS_YN", nullable = false, length = 1)
    @Comment("로그인성공여부")
    private boolean LGN_SCS_YN;
    
    /***
     * @author 140024
     * @implNote 생성시간(인증시간)
     * @since 2024-06-09
     */
    @Column(name = "GNT_DTM", updatable = false, columnDefinition = "DATE")
    @Comment("생성일시")
    private LocalDate GNT_DTM;

}