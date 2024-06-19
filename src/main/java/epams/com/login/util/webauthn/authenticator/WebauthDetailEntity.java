package epams.com.login.util.webauthn.authenticator;

import com.yubico.webauthn.data.ByteArray;

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
    private Long SEQ_ID;

    @Lob
    @Column(nullable = false)
    private ByteArray CRDT_ID;

    @Lob
    @Column(nullable = false)
    private ByteArray PBLK_KEY;

	@Column(nullable = false)
	private Long CNT;
	
	@Lob
	@Column(nullable = true)
	private ByteArray AAGUID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="EMP_NO", insertable = false, updatable = false)
    private WebauthUserEntity user;
    
    /***
     * @author 140024
     * @implNote lombok getter에서 자동으로 인식하지 못하는 문제로 별도 추가
     * @since 2024-06-10
     */
    public String getUser() {
        return user != null ? user.getEMP_NO() : null;
    }
    
    /***
     * @author 140024
     * @implNote lombok setter에서 자동으로 인식하지 못하는 문제로 별도 추가
     * @since 2024-06-10
     */
    public void setUser(final String empNo) {
        if (this.user == null) {
            this.user = new WebauthUserEntity();
        }
        this.user.setEMP_NO(empNo);
    }

}
