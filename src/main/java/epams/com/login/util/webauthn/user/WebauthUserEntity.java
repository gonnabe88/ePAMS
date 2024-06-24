package epams.com.login.util.webauthn.user;

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
    @Column(name = "EMP_NO", nullable = false, unique = true)
    private String EMP_NO;

    @Column(name = "DISP_NM", nullable = false)
    private String DISP_NM;

    @Lob
    @Column(name = "HANDLE", nullable = false)
    private byte[] HANDLE;
}
