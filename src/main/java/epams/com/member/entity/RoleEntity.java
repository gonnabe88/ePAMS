package epams.com.member.entity;

import org.hibernate.annotations.Comment;

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
@Table(name = "THURXE_CROLEM")
@Comment("인사_외부근태 역할메인기본")
public class RoleEntity extends BaseEntity{

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
    @Column(name = "ATH_ID", nullable = false, length = 32)
    @Comment("권한ID")
    private String ATH_ID;

}
