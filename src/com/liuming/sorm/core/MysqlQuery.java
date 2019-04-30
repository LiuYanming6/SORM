package com.liuming.sorm.core;

import com.liuming.sorm.bean.ColumnInfo;
import com.liuming.sorm.bean.TableInfo;
import com.liuming.sorm.utils.StringUtils;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Author: 刘艳明
 * @Date: 19-4-29 下午4:21
 */
public class MysqlQuery implements Query {
    /**
     * 直接执行一个DML语句
     *
     * @param sql    sql语句
     * @param params 参数
     * @return 执行sql语句影响记录的行数
     */
    @Override
    public int executeDML(String sql, Object[] params) {
        return 0;
    }

    /**
     * 将一个对象存储到数据库中
     *
     * @param obj 要存储的对象
     */
    @Override
    public void insert(Object obj) {
        Class clazz = obj.getClass();
        TableInfo tableInfo = TableContext.classTableInfoMap.get(clazz);
        ColumnInfo priKey = tableInfo.getOnlyPriKey();

        //通过反射机制,调用属性对应的get和set方法
        try {
            assert clazz != null;
            Method method = clazz.getMethod("get" +
                    StringUtils.firstChar2UpperCase(priKey.getName()));
            Object priKeyValue = method.invoke(obj);

            delete(clazz, priKeyValue);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除clazz表示类对应表中的记录(指定主键id的记录)
     *
     * @param clazz 表对应类
     */
    @Override
    public void delete(Class clazz) {
    }

    /**
     * 删除clazz表示类对应表中的记录(指定主键id的记录)
     *
     * @param clazz 表对应类
     * @param id    主键
     */
    @Override
    public void delete(Class clazz, Object id) {
        TableInfo tableInfo = TableContext.classTableInfoMap.get(clazz);
        ColumnInfo priKey = tableInfo.getOnlyPriKey();

        String sql = "delete from " + tableInfo.getTname() +
                " where " + priKey.getName() + " =? ";
        executeDML(sql, new Object[]{id});
    }

    /**
     * 更新对象对应的记录
     *
     * @param obj        更新的对象
     * @param fieldNames 指定字段
     * @return 影响记录的行数
     */
    @Override
    public int update(Object obj, String[] fieldNames) {
        return 0;
    }

    /**
     * 查询结果分类
     * 多行多列 - List<Javabean>
     * 一行多列 - Javabean
     * 一行一列 - 普通对象Object, 数字Number
     *
     * @param sql    查询语句
     * @param clazz  封装数据的javabean
     * @param params sql的参数
     * @return 结果
     */
    @Override
    public List queryRows(String sql, Class clazz, Object[] params) {
        return null;
    }

    /**
     * 一行一列
     *
     * @param sql    查询语句
     * @param params sql的参数
     * @return 结果
     */
    @Override
    public Object queryOneRow(String sql, Object[] params) {
        return null;
    }

    /**
     * 一行一列
     *
     * @param sql    查询语句
     * @param params sql的参数
     * @return 结果
     */
    @Override
    public Number queryNumber(String sql, Object[] params) {
        return null;
    }
}
