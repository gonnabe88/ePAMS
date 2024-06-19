package epams.com.login.util.webauthn.utility;

import com.yubico.webauthn.data.ByteArray;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.*;

@MappedTypes(ByteArray.class)
@MappedJdbcTypes(JdbcType.BLOB)
public class ByteArrayTypeHandler extends BaseTypeHandler<ByteArray> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, ByteArray parameter, JdbcType jdbcType) throws SQLException {
        ps.setBytes(i, parameter.getBytes());
    }

    @Override
    public ByteArray getNullableResult(ResultSet rs, String columnName) throws SQLException {
        byte[] bytes = rs.getBytes(columnName);
        return bytes != null ? new ByteArray(bytes) : null;
    }

    @Override
    public ByteArray getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        byte[] bytes = rs.getBytes(columnIndex);
        return bytes != null ? new ByteArray(bytes) : null;
    }

    @Override
    public ByteArray getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        byte[] bytes = cs.getBytes(columnIndex);
        return bytes != null ? new ByteArray(bytes) : null;
    }
}
