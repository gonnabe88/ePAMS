package epams.com.login.util.webauthn.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

import com.yubico.webauthn.data.ByteArray;
import com.yubico.webauthn.data.UserIdentity;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "HURXE_WWUSRM")
public class AppUser {
    
    @Id
    @Column(name = "EMP_NO", nullable = false, unique = true)
    private String username;

    @Column(name = "DISP_NM", nullable = false)
    private String displayName;

    @Lob
    @Column(name = "HANDLE", nullable = false, length = 64)
    private ByteArray handle;

    public AppUser(UserIdentity user) {
        this.handle = user.getId();
        this.username = user.getName();
        this.displayName = user.getDisplayName();
    }

    public UserIdentity toUserIdentity() {
        return UserIdentity.builder()
            .name(getUsername())
            .displayName(getDisplayName())
            .id(getHandle())
            .build();
    }
}