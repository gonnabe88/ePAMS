package epams.domain.com.login.util.webauthn.authenticator;

import epams.domain.com.login.util.webauthn.user.AppUser;
import epams.model.entity.BaseEntity;
import org.hibernate.annotations.Comment;

import com.yubico.webauthn.data.ByteArray;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author K140024
 * @implNote Webauthn 자격증명을 나타내는 엔티티 클래스
 * @since 2024-06-11
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "THURXE_CWCRDM")
@Comment("인사_외부근태 Webauthn자격증명기본")
public class Authenticator extends BaseEntity {

    /**
     * @author K140024
     * @implNote Webauthn 일련번호(WEBAUTHN_SNO)
     * @since 2024-06-11
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    @Column(name = "WEBAUTHN_SNO", columnDefinition = "NUMBER(10)")
    @Comment("Webauthn 일련번호")
    private Long seqId;

    /**
     * @author K140024
     * @implNote Webauthn 자격증명 ID(WEBAUTHN_CRDT_ID)
     * @since 2024-06-11
     */
    @Lob
    @Column(name = "WEBAUTHN_CRDT_ID", nullable = false)
    @Comment("Webauthn 자격증명 ID")
    private ByteArray credentialId;

    /**
     * @author K140024
     * @implNote Webauthn 공개키(WEBAUTHN_PBLK_KEY)
     * @since 2024-06-11
     */
    @Lob
    @Column(name = "WEBAUTHN_PBLK_KEY", nullable = false)
    @Comment("Webauthn 공개키")
    private ByteArray publicKey;

    /**
     * @author K140024
     * @implNote Webauthn 자격증명수(WEBAUTHN_CNT)
     * @since 2024-06-11
     */
    @Column(name = "WEBAUTHN_CNT", nullable = false, columnDefinition = "NUMBER(10)")
    @Comment("Webauthn 자격증명수")
    private Long count;

    /**
     * @author K140024
     * @implNote Webauthn Authenticator Attestation GUID(WEBAUTHN_AAGUID)
     * @since 2024-06-11
     */
    @Lob
    @Column(name = "WEBAUTHN_AAGUID", nullable = true)
    @Comment("Webauthn Authenticator Attestation GUID")
    private ByteArray aaguid;

    /**
     * @author K140024
     * @implNote 직원번호(ENO)
     * @since 2024-06-11
     */
    @ManyToOne(targetEntity = AppUser.class)
    @JoinColumn(name = "ENO", nullable = false)
    @Comment("직원번호")
    private AppUser user;

    /**
     * @author K140024
     * @implNote Authenticator 생성자
     * @since 2024-06-11
     */
    public Authenticator(
    		final ByteArray credentialId,
    		final ByteArray publicKey,
    		final ByteArray aaguid,
    		final Long count,
    		final AppUser user) {
    	super();
    	this.credentialId = credentialId;
        this.publicKey = publicKey;
        this.aaguid = aaguid;
        this.count = count;
        this.user = user;
    }
}
