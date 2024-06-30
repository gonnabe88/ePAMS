package epams.com.member.entity;

import java.time.LocalDate;

import org.hibernate.annotations.Comment;
import org.hibernate.annotations.UpdateTimestamp;

import epams.com.board.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/***
 * @author 140024
 * @implNote 사용자
 * @since 2024-06-30
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "THURXE_CUSERM")
@Comment("인사_외부근태 사용자메인기본")
public class MemberEntity extends BaseEntity{

    /***
     * @author 140024
     * @implNote 사원번호
     * @since 2024-06-30
     */
    @Id 
    @Column(name = "ENO", nullable = false, unique = true, length = 32)
    @Comment("사원번호")
    private String ENO;

    /***
     * @author 140024
     * @implNote 관리자여부
     * @since 2024-06-30
     */
    @Column(name = "ADMIN_YN", nullable = false, length = 1)
    @Comment("관리자여부")
    private String ADMIN_YN = "ADMIN_YN";

    /***
     * @author 140024
     * @implNote 마지막로그인일시
     * @implSpec 외래키
     * @since 2024-06-30
     */
    @UpdateTimestamp
    @Column(name = "LAST_LOGIN_DATE", columnDefinition = "DATE")
    @Comment("마지막로그인일시")
    private LocalDate LAST_LOGIN_DATE;


}
