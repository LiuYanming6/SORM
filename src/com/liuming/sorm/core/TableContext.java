package com.liuming.sorm.core;

import com.liuming.sorm.bean.ColumnInfo;
import com.liuming.sorm.bean.TableInfo;
import com.liuming.sorm.utils.JavaFileUtils;
import com.liuming.sorm.utils.StringUtils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 负责获取管理数据库所有表结构和类结构的关系,并可以根据表结构生成类结构
 *
 * @author liuming
 */
public class TableContext {
    /**
     * 表名为key,表信息对象为value
     */
    public static Map<String, TableInfo> tableInfoMap = new HashMap<>();
    /**
     * 将po的class对象和表信息对象关联起来,便于重用
     * HashMap是线程不安全的
     */
    public static Map<Class, TableInfo> classTableInfoMap = new HashMap<>();

    private TableContext() {
    }

    static {
        Connection conn = DBManager.getConnection();
        DatabaseMetaData dbmd;
        try {
            assert conn != null;
            dbmd = conn.getMetaData();
            ResultSet tableSet = dbmd.getTables(null, "%", "%", new String[]{"TABLE"});
            while (tableSet.next()) {
                String tableName = tableSet.getString("TABLE_NAME");
//                Unknown table 'user' in information_schema
                System.out.println(tableName);
                TableInfo tableInfo = new TableInfo(tableName,
                        new HashMap<String, ColumnInfo>(),
                        new ArrayList<ColumnInfo>());
                tableInfoMap.put(tableName, tableInfo);

                ResultSet set = dbmd.getColumns(null, "%s", tableName, "%");
                while (set.next()) {
                    String columnName = set.getString("COLUMN_NAME");
                    ColumnInfo columnInfo = new ColumnInfo(
                            columnName,
                            set.getString("TYPE_NAME"),
                            0);
                    tableInfo.getColumns().put(columnName, columnInfo);
                }

                ResultSet keyset = dbmd.getPrimaryKeys(null, "%", tableName);
                while (keyset.next()) {
                    Object key = keyset.getObject("COLUMN_NAME");
//                    String key = keyset.getString("COLUMN_NAME");
                    ColumnInfo keyColumn = tableInfo.getColumns().get(key);
                    keyColumn.setKeyType(1); //设置主键
                    tableInfo.getPriKeys().add(keyColumn);
                }
                if (tableInfo.getPriKeys().size() > 0) {
                    //取唯一主键,方便使用
                    tableInfo.setOnlyPriKey(tableInfo.getPriKeys().get(0));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //更新类结构
        updateJavaPOFile();

        //加载po包下面所有的类,便于重用,提高效率
        loadPOTables();
    }

    /**
     * 更新持久化对象
     */
    public static void updateJavaPOFile() {
        Map<String, TableInfo> tables = TableContext.tableInfoMap;

        for (TableInfo t : tables.values()) {
            JavaFileUtils.createJavaPOFile(t, new MySqlTypeConvertor());
            System.out.println("生成表{" + t.getTname() + "}对象对应的java bean");
        }

    }

    /**
     * 加载po包下的类
     */
    public static void loadPOTables() {
        Class clazz = null;
        for (TableInfo t : tableInfoMap.values()) {
            try {
                clazz = Class.forName(DBManager.getConf().getPoPackage() + "." +
                        StringUtils.firstChar2UpperCase(t.getTname()));
                classTableInfoMap.put(clazz, t);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        updateJavaPOFile();
    }
}
