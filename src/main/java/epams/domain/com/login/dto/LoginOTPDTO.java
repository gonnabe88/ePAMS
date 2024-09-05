package epams.domain.com.login.dto;

import epams.model.vo.LoginOTPVO;
import groovy.transform.builder.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/***
 * @author 140024
 * @implNote OEHR.THURXE_COTPIM (otp 발급내역)
 * @since 2024-06-09
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@Data
public class LoginOTPDTO extends LoginOTPVO {
    
}
