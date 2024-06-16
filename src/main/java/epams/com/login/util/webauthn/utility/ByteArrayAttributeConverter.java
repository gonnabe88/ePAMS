package epams.com.login.util.webauthn.utility;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import com.yubico.webauthn.data.ByteArray;

@Converter(autoApply = true)
public class ByteArrayAttributeConverter implements AttributeConverter<ByteArray, byte[]> {

    @Override
    public byte[] convertToDatabaseColumn(ByteArray attribute) {
        return attribute.getBytes();
    }

    @Override
    public ByteArray convertToEntityAttribute(byte[] dbData) {
        return new ByteArray(dbData);
    }

}
