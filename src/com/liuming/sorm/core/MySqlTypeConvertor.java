package com.liuming.sorm.core;

/**
 * @Author: 刘艳明
 * @Date: 19-4-24 下午9:11
 */
public class MySqlTypeConvertor implements TypeConvertor {
    /**
     * 将数据库类型转换成java的数据类型
     *
     * @param columnType 数据库字段类型
     * @return java数据类型
     */
    @Override
    public String databaseType2JavaType(String columnType) {
        //varchar -> String
        if ("varchar".equalsIgnoreCase(columnType)
                || "char".equalsIgnoreCase(columnType)) {
            return "String";
        } else if ("int".equalsIgnoreCase(columnType)
                || "tinyint".equalsIgnoreCase(columnType)
                || "smallint".equalsIgnoreCase(columnType)
                || "integer".equalsIgnoreCase(columnType)) {
            return "Integer";
        } else if ("bigint".equalsIgnoreCase(columnType)){
            return "Long";
        } else if("double".equalsIgnoreCase(columnType)
                || "float".equalsIgnoreCase(columnType)) {
            return "Double";
        } else if ("clob".equalsIgnoreCase(columnType)) {
            return "java.sql.Clob";
        }else if ("blob".equalsIgnoreCase(columnType)) {
            return "java.sql.Blob";
        }else if ("date".equalsIgnoreCase(columnType)) {
            return "java.sql.Date";
        }else if ("time".equalsIgnoreCase(columnType)) {
            return "java.sql.Time";
        }else if ("timestamp".equalsIgnoreCase(columnType)) {
            return "java.sql.Timestamp";
        }

        return null;
    }

    /**
     * 将java的数据类型转换成数据库类型
     *
     * @param javaDataType java数据类型
     * @return 数据库字段类型
     */
    @Override
    public String javaType2DatabaseType(String javaDataType) {
        return null;
    }
}
