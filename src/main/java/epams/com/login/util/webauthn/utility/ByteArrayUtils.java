package epams.com.login.util.webauthn.utility;

/**
 * @author K140024
 * @implNote ByteArrayUtils 클래스는 Byte 배열과 byte 배열 간의 변환 유틸리티를 제공합니다.
 * @since 2024-06-11
 */
public final class ByteArrayUtils {

    /**
     * 기본 생성자를 private으로 선언하여 인스턴스화되지 않도록 합니다.
     * 
     * @since 2024-06-11
     */
    private ByteArrayUtils() {
        throw new UnsupportedOperationException("Utility class");
    }
	
    /**
     * Byte 객체 배열을 byte 기본형 배열로 변환합니다.
     * 
     * @param byteObjects Byte 객체 배열
     * @return byte 기본형 배열
     * @since 2024-06-11
     */
    public static byte[] toPrimitive(final Byte[] byteObjects) {
        final byte[] bytes = new byte[byteObjects.length];
        for (int i = 0; i < byteObjects.length; i++) {
            bytes[i] = byteObjects[i];
        }
        return bytes;
    }

    /**
     * byte 기본형 배열을 Byte 객체 배열로 변환합니다.
     * 
     * @param bytes byte 기본형 배열
     * @return Byte 객체 배열
     * @since 2024-06-11
     */
    public static Byte[] toObject(final byte[] bytes) {
        final Byte[] byteObjects = new Byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            byteObjects[i] = bytes[i];
        }
        return byteObjects;
    }
}
