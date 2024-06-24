package epams.com.login.util.webauthn.authenticator;

import epams.com.login.util.webauthn.user.WebauthUserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "COM_WEBAUTH_DTL")
public class WebauthDetailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    @Column(name = "SEQ_ID")
    private Long SEQ_ID;

    @Lob
    @Column(name = "CRDT_ID", nullable = false)
    private byte[] CRDT_ID;

    @Lob
    @Column(name = "PBLK_KEY", nullable = false)
    private byte[] PBLK_KEY;

	@Column(name = "CNT", nullable = false)
	private Long CNT;
	
	@Lob
	@Column(name = "AAGUID", nullable = true)
	private byte[] AAGUID;

	@ManyToOne(fetch = FetchType.EAGER) // 즉시 로딩으로 설정
	@JoinColumn(name="EMP_NO", referencedColumnName = "EMP_NO", insertable = false, updatable = false)
    private WebauthUserEntity user;


    /***
     * @author 140024
     * @implNote lombok getter에서 자동으로 인식하지 못하는 문제로 별도 추가
     * @since 2024-06-10
     */
    public String getEMP_NO() {
        return user != null ? user.getEMP_NO() : null;
    }
    
    /***
     * @author 140024
     * @implNote lombok setter에서 자동으로 인식하지 못하는 문제로 별도 추가
     * @since 2024-06-10
     */
    public void setEMP_NO(final String username) {
        if (this.user == null) {
            this.user = new WebauthUserEntity();
        }
        this.user.setEMP_NO(username);
    }
    
}
