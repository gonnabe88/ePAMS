package epams.domain.com.member.dto;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import groovy.transform.builder.Builder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import epams.model.vo.IamUserVO;

/***
 * 사용자 정보를 담는 DTO 클래스입니다.
 * 
 * @author 140024
 * @implNote 사용자
 * @since 2024-06-30
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Builder
@Data
public class IamUserDTO extends IamUserVO {

    /***
     * 2차인증 유형
     * 
     * @author 140024
     * @since 2024-06-30
     */
    private String MFA;

    /***
     * 로그인 유형
     *
     * @author 140024
     * @since 2024-08-11
     */
    private boolean isAdmin = false;

    /***
     * 구글 libphonenumber을 이용한 전화번호 포맷팅
     *
     * @author 140024
     * @since 2024-10-12
     */
    public void formatContactNumber(String phoneNo, String inlineNo, String regionCode) {
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        try {
            Phonenumber.PhoneNumber phoneNumber = phoneUtil.parse(phoneNo, regionCode);
            Phonenumber.PhoneNumber inlineNumber = phoneUtil.parse(phoneNo, regionCode);

            if(phoneUtil.isValidNumber(phoneNumber)) {
                this.setPhoneNo(phoneUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.NATIONAL));
            }
            if(phoneUtil.isValidNumber(inlineNumber)) {
                this.setInlineNo(phoneUtil.format(inlineNumber, PhoneNumberUtil.PhoneNumberFormat.NATIONAL));
            }
        } catch (NumberParseException e) {
            e.printStackTrace();
        }
    }
}
