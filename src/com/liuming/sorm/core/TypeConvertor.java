package com.liuming.sorm.core;

/**
 * 负责java数据类型和数据库类型的互相转换
 */
public interface TypeConvertor {
    /**
     * 将数据库类型转换成java的数据类型
     * @param columnType 数据库字段类型
     * @return java数据类型
     */
    String databaseType2JavaType(String columnType);

    /**
     * 将java的数据类型转换成数据库类型
     * @param javaDataType java数据类型
     * @return 数据库字段类型
     */
    String javaType2DatabaseType(String javaDataType);
}
