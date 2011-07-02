package org.wooddog.dao.typehandlers;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 19-06-11
 * Time: 18:39
 * To change this template use File | Settings | File Templates.
 */
public class UrlTypeHandler implements TypeHandler {
    public void setParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, ((URL) parameter).toExternalForm());
    }

    public Object getResult(ResultSet rs, String columnName) throws SQLException {
        String value;

        value = rs.getString(columnName);

        try {
            return new URL(value);
        } catch (MalformedURLException x) {
            throw new SQLException("invalid data, " + value + " is not a valid url, " + x.getMessage(), x);
        }
    }

    public Object getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return cs.getString(columnIndex);
    }
}
