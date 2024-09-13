package epams.domain.com.login.util;

/**
 * @author K140024
 * @implNote 휴대폰 번호를 Masking 하는 유틸리티
 * @since 2024-09-05
 */
public class MaskPhoneNoUtil {

    public static String maskPhoneNo(String phoneNo) {

        // 마스킹된 휴대폰 번호 (리턴값)
        String maskedPhoneNo = null;
        

        // 입력값이 null이거나 길이가 10자리 이하인 경우 그대로 반환
        if(phoneNo == null || phoneNo.length() <= 4) {
            maskedPhoneNo = phoneNo;
        }
        // 길이가 11자리이고 010으로 시작하는 경우
        else if(phoneNo.length() == 11 || phoneNo.startsWith("010")) {
            // ex) 010-****-1234 형식
            maskedPhoneNo = "010-****-"+phoneNo.substring(7);
        }
        // 그 외의 경우, 끝 4자리를 제외한 나머지를 *로 반환
        else {
            // ex) *********1234 형식
            StringBuilder maskedPhoneNoSB = new StringBuilder();
            for(int i=0 ; i<phoneNo.length()-4 ; i++) {
                maskedPhoneNoSB.append("*");
            }
            maskedPhoneNoSB.append(phoneNo.substring(phoneNo.length()-4));
            maskedPhoneNo = maskedPhoneNoSB.toString();
        }

        return maskedPhoneNo;
    }
    
}
