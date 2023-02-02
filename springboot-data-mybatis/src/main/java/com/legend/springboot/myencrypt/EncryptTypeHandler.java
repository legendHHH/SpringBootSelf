package com.legend.springboot.myencrypt;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.nio.charset.StandardCharsets;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 加解密TypeHandler
 *
 * @author legend
 * @version 1.0
 * @MappedTypes ：表示该处理器处理的java类型是什么。
 * @MappedJdbcTypes ：表示处理器处理的Jdbc类型。
 * @description
 * @date 2022/10/19
 */
@MappedJdbcTypes(JdbcType.VARCHAR)
@MappedTypes(Encrypt.class)
public class EncryptTypeHandler extends BaseTypeHandler<Encrypt> {

    private static final byte[] KEYS = "34567890qwertfgh".getBytes(StandardCharsets.UTF_8);

    /**
     * 设置参数
     */
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Encrypt parameter, JdbcType jdbcType) throws SQLException {
        if (parameter == null || parameter.getValue() == null) {
            preparedStatement.setString(i, null);
            return;
        }
        AES aes = SecureUtil.aes(KEYS);
        String encrypt = aes.encryptHex(parameter.getValue());
        preparedStatement.setString(i, encrypt);
    }

    /**
     * 获取值
     */
    @Override
    public Encrypt getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        return this.decrypt(resultSet.getString(columnName));
    }

    /**
     * 获取值
     */
    @Override
    public Encrypt getNullableResult(ResultSet resultSet, int columnIndex) throws SQLException {
        return this.decrypt(resultSet.getString(columnIndex));
    }

    /**
     * 获取值
     */
    @Override
    public Encrypt getNullableResult(CallableStatement callableStatement, int columnIndex) throws SQLException {
        return this.decrypt(callableStatement.getString(columnIndex));
    }

    /**
     * 解密值通用方法
     */
    private Encrypt decrypt(String value) {
        if (value == null) {
            return null;
        }
        return new Encrypt(SecureUtil.aes(KEYS).decryptStr(value));
    }
}
