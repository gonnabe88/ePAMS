package epams.com.login.util.webauthn.user;

import com.yubico.webauthn.data.ByteArray;
import com.yubico.webauthn.data.UserIdentity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "COM_WEBAUTH_USR")
public class WebauthUserEntity {
    
    @Id
    @Column(nullable = false, unique = true)
    private String EMP_NO;

    @Column(nullable = false)
    private String DISP_NM;

    @Lob
    @Column(nullable = false, length = 64)
    private ByteArray HANDLE;

}