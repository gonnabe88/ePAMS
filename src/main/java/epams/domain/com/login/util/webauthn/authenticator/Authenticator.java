package epams.domain.com.login.util.webauthn.authenticator;

import com.yubico.webauthn.data.ByteArray;
import epams.domain.com.login.util.webauthn.user.AppUser;
import epams.model.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

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
    @Column(name = "WEBA_SNO", columnDefinition = "NUMBER(10)")
    @Comment("Webauthn 일련번호")
    private Long seqId;

    /**
     * @author K140024
     * @implNote Webauthn 자격증명 ID(WEBAUTHN_CRDT_ID)
     * @since 2024-06-11
     */
    @Lob
    @Column(name = "WEBA_CFA_IMG", nullable = false)
    @Comment("Webauthn인증서이미지")
    private ByteArray credentialId;

    /**
     * @author K140024
     * @implNote Webauthn 공개키(WEBAUTHN_PBLK_KEY)
     * @since 2024-06-11
     */
    @Lob
    @Column(name = "WEBA_OPNK_IMG", nullable = false)
    @Comment("Webauthn공개키이미지")
    private ByteArray publicKey;

    /**
     * @author K140024
     * @implNote Webauthn 자격증명수(WEBAUTHN_CNT)
     * @since 2024-06-11
     */
    @Column(name = "WEBA_CER_CNT", nullable = false, columnDefinition = "NUMBER(10)")
    @Comment("Webauthn인증건수")
    private Long count;

    /**
     * @author K140024
     * @implNote Webauthn Authenticator Attestation GUID(WEBAUTHN_AAGUID)
     * @since 2024-06-11
     */
    @Lob
    @Column(name = "WEBA_CER_GUID_IMG", nullable = true)
    @Comment("Webauthn인증GUID이미지")
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
