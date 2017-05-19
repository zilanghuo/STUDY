package com.zdmoney.message.common.base;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.lang.reflect.Method;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class EnumValueHandler<E extends Enum<E>> extends BaseTypeHandler<E> {

    private Class<E> type;
    private final E[] enums;

    public EnumValueHandler(Class<E> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.type = type;
        this.enums = type.getEnumConstants();
        if (this.enums == null){
            throw new IllegalArgumentException(type.getSimpleName() + " does not represent an enum type.");
        }
    }


    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        int order = parameter.ordinal();
        Object o = null;
        try {
            Method getOrder = type.getMethod("getValue");
            o = getOrder.invoke(parameter);
        } catch (Exception e) {
            throw new IllegalArgumentException("getValue method cannot get");
        }

        if(o instanceof Integer) {
            ps.setInt(i, (Integer) o);
        } else if(o instanceof String) {
            ps.setString(i, (String) o);
        }
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int value = rs.getInt(columnName);
        if (rs.wasNull()) {
            return null;
        } else {
            return parseValue(value);
        }
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int value = rs.getInt(columnIndex);
        if (rs.wasNull()) {
            return null;
        } else {
            return parseValue(value);
        }
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int value = cs.getInt(columnIndex);
        if (cs.wasNull()) {
            return null;
        } else {
            return parseValue(value);
        }
    }

    private E parseValue(int value) throws SQLException {
        try {
            for (E anEnum : enums) {
                try {
                    Method getValue = type.getMethod("getValue");

                    Object o = getValue.invoke(anEnum);
                    if(o instanceof Integer) {
                        int anValue = (int)o ;
                        if (anValue == value) {
                            return anEnum;
                        }
                    } else if(o instanceof String) {
                        String anValue = (String)o ;
                        if (String.valueOf(value).equals(anValue)) {
                            return anEnum;
                        }
                    }
                } catch (Exception e) {
                    return enums[value];
                }
            }
        } catch (Exception ex) {
            throw new IllegalArgumentException("Cannot convert " + value + " to " + type.getSimpleName() + " by ordinal value.", ex);
        }
        throw new IllegalArgumentException("Cannot convert " + value + " to " + type.getSimpleName() + " by ordinal value.");
    }

}
