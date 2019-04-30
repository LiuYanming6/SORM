package com.liuming.sorm.core;

import java.awt.*;

/**
 * 负责查询 - 对外提供服务的核心类
 *
 * @author liuming
 */
public interface Query {
    /**
     * 直接执行一个DML语句
     *
     * @param sql    sql语句
     * @param params 参数
     * @return 执行sql语句影响记录的行数
     */
    int executeDML(String sql, Object[] params);

    /**
     * 将一个对象存储到数据库中
     *
     * @param obj 要存储的对象
     */
    void insert(Object obj);

    /**
     * 删除clazz表示类对应表中的记录(指定主键id的记录)
     *
     * @param clazz 表对应类
     */
    void delete(Class clazz);

    /**
     * 删除clazz表示类对应表中的记录(指定主键id的记录)
     *
     * @param clazz 表对应类
     * @param id    主键
     */
    void delete(Class clazz, Object id);

    /**
     * 更新对象对应的记录
     *
     * @param obj        更新的对象
     * @param fieldNames 指定字段
     * @return 影响记录的行数
     */
    int update(Object obj, String[] fieldNames);

    /**
     * 查询结果分类
     * 多行多列 - List<Javabean>
     * 一行多列 - Javabean
     * 一行一列 - 普通对象Object, 数字Number
     *
     * @param sql 查询语句
     * @param clazz 封装数据的javabean
     * @param params sql的参数
     * @return 结果
     */
    List queryRows(String sql, Class clazz, Object[] params);

    /**
     * 一行一列
     * @param sql 查询语句
     * @param params sql的参数
     * @return 结果
     */
    Object queryOneRow(String sql, Object[] params);

    /**
     * 一行一列
     * @param sql 查询语句
     * @param params sql的参数
     * @return 结果
     */
    Number queryNumber(String sql, Object[] params);
}
