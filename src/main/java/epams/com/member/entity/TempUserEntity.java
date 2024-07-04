package epams.com.member.entity;

import java.io.Serializable;

import epams.com.member.dto.TempRoleDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author K140024
 * @implNote 직원정보 entity (나중에 지워야 함)
 * @since 2024-06-11
 */
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Table(name = "member")
public class TempUserEntity implements Serializable {

    /**
	 * VersionID
	 */
	private static final long serialVersionUID = 5837283909818295247L;

	/**
     * @author K140024
     * @implNote 사용자 이름
     * @since 2024-06-11
     */
    @Id
    private String username;

    /**
     * @author K140024
     * @implNote 사용자 비밀번호
     * @since 2024-06-11
     */
    @Column
    private String password;

    /**
     * @author K140024
     * @implNote 부서명
     * @since 2024-06-11
     */
    @Column
    private String dept;

    /**
     * @author K140024
     * @implNote 팀명
     * @since 2024-06-11
     */
    @Column
    private String team;

    /**
     * @author K140024
     * @implNote 계정 활성화 여부
     * @since 2024-06-11
     */
    @Column
    private boolean enabled;

    /**
     * @author K140024
     * @implNote 사용자 역할
     * @since 2024-06-11
     */
    @Enumerated(EnumType.STRING)
    private TempRoleDTO role;

    /**
     * @author K140024
     * @implNote 사용자 UUID
     * @since 2024-06-11
     */
    @Column
    private String uuid;

    /**
     * @author K140024
     * @implNote 사용자 책임
     * @since 2024-06-11
     */
    @Column
    private String responsibility;

    /**
     * @author K140024
     * @implNote 사용자 정보를 기반으로 RoleEntity 객체를 생성하는 생성자
     * @since 2024-06-11
     */
    @Builder
    public TempUserEntity(final String username, final String password, final String dept, final String team, final String responsibility, final boolean enabled, final TempRoleDTO role, final String uuid) {
        this.username = username;
        this.password = password;
        this.dept = dept;
        this.team = team;
        this.responsibility = responsibility;
        this.enabled = enabled;
        this.role = role;
        this.uuid = uuid;
    }

}
