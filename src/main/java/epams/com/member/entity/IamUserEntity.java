package epams.com.member.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
public class IamUserEntity{

    /***
     * @author 140024
     * @implNote 행번
     * @since 2024-06-30
     */
    @Id 
    @Column(name = "EMP_NO")
    private String EMP_NO;

    /***
     * @author 140024
     * @implNote 사용자명
     * @since 2024-06-30
     */
    @Column(name = "EMP_NM")
    private String EMP_NM;

    /***
     * @author 140024
     * @implNote 패스워드
     * @since 2024-06-30
     */
    @Column(name = "EMP_PWD")
    private String EMP_PWD;

    /***
     * @author 140024
     * @implNote 생성일자
     * @since 2024-06-30
     */
    @Column(name = "TZ_DATE")
    private LocalDate TZ_DATE;

    
}
