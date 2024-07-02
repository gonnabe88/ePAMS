/**
 * @author K140024
 * @implNote 2단계 인증 로직 (Custom)에서 ByteArray 타입을 처리하기 위한 MyBatis 타입 핸들러.
 *            이 핸들러는 데이터베이스에서 ByteArray 데이터를 저장하고 조회할 때 사용됩니다.
 * @since 2024-06-20
 */
package epams.com.login.util.webauthn.utility;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import com.yubico.webauthn.data.ByteArray;
import java.sql.*;

public class ByteArrayTypeHandler extends BaseTypeHandler<ByteArray> {

    /**
     * ByteArray 데이터를 PreparedStatement에 설정합니다.
     * 이 메소드는 MyBatis가 데이터베이스에 데이터를 저장할 때 호출됩니다.
     * 
     * @throws SQLException SQL 처리 중 예외 발생
     */
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, ByteArray parameter, JdbcType jdbcType) throws SQLException {
        ps.setBytes(i, parameter.getBytes());
    }

    /**
     * ResultSet에서 columnName을 기준으로 ByteArray 데이터를 추출합니다.
     * 이 메소드는 MyBatis가 데이터베이스에서 데이터를 조회할 때 호출됩니다.
     * 
     * @return           조회된 데이터를 바탕으로 생성된 ByteArray 객체, 또는 null
     * @throws SQLException SQL 처리 중 예외 발생
     */
    @Override
    public ByteArray getNullableResult(ResultSet rs, String columnName) throws SQLException {
        byte[] bytes = rs.getBytes(columnName);
        return bytes == null ? null : new ByteArray(bytes);
    }

    /**
     * ResultSet에서 columnIndex를 기준으로 ByteArray 데이터를 추출합니다.
     * 이 메소드는 MyBatis가 데이터베이스에서 데이터를 조회할 때 호출됩니다.
     * 
     * @return             조회된 데이터를 바탕으로 생성된 ByteArray 객체, 또는 null
     * @throws SQLException SQL 처리 중 예외 발생
     */
    @Override
    public ByteArray getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        byte[] bytes = rs.getBytes(columnIndex);
        return bytes == null ? null : new ByteArray(bytes);
    }

    /**
     * CallableStatement에서 columnIndex를 기준으로 ByteArray 데이터를 추출합니다.
     * 이 메소드는 MyBatis가 프로시저나 함수 호출을 통해 데이터를 조회할 때 사용됩니다.
     * 
     * @return            조회된 데이터를 바탕으로 생성된 ByteArray 객체, 또는 null
     * @throws SQLException SQL 처리 중 예외 발생
     */
    @Override
    public ByteArray getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        byte[] bytes = cs.getBytes(columnIndex);
        return bytes == null ? null : new ByteArray(bytes);
    }
}
