package epams.domain.com.login.util.webauthn.utility;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.NoArgsConstructor;
import com.yubico.webauthn.data.ByteArray;

/**
 * Webauthn 관련 유틸리티 클래스를 제공하는 서비스
 * JPA AttributeConverter를 사용하여 ByteArray를 데이터베이스에 저장하기 위한 byte[]로 변환하고, 
 * 그 반대로 변환하는 역할을 합니다.
 * @author K140024
 * @implNote Webauthn 관련 유틸리티 클래스를 제공하는 서비스
 * @since 2024-06-11
 */
@NoArgsConstructor
@Converter(autoApply = true)
public class ByteArrayAttributeConverter implements AttributeConverter<ByteArray, byte[]> {

    /**
     * ByteArray 속성을 데이터베이스에 저장하기 위한 byte 배열로 변환합니다.
     * @param attribute 변환할 ByteArray 속성
     * @return ByteArray 속성의 byte 배열 표현
     */
    @Override
    public byte[] convertToDatabaseColumn(final ByteArray attribute) {
        return attribute.getBytes();
    }

    /**
     * 데이터베이스의 byte 배열을 ByteArray 엔티티 속성으로 변환합니다.
     * @param dbData 변환할 데이터베이스의 byte 배열
     * @return byte 배열의 ByteArray 객체 표현
     */
    @Override
    public ByteArray convertToEntityAttribute(final byte[] dbData) {
        return new ByteArray(dbData);
    }
}
