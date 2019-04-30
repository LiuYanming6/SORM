package com.liuming.sorm.utils;

import com.liuming.sorm.bean.ColumnInfo;
import com.liuming.sorm.bean.JavaFieldGetSet;
import com.liuming.sorm.bean.TableInfo;
import com.liuming.sorm.core.DBManager;
import com.liuming.sorm.core.TypeConvertor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
 * 封装了生成java文件常用的操作
 */
public class JavaFileUtils {
    /**
     * 根据字段信息生成java属性信息
     *
     * @param columnInfo 字段信息
     * @param convertor  类型转化器
     * @return java属性和set/get方法源码
     */
    public static JavaFieldGetSet createJavaFieldGetSetSRC(ColumnInfo columnInfo, TypeConvertor convertor) {
        JavaFieldGetSet jfgs = new JavaFieldGetSet();

        String javaFieldType = convertor.databaseType2JavaType(columnInfo.getDataType());
        jfgs.setFieldInfo("\tprivate " +
                javaFieldType + " " +
                columnInfo.getName() + ";\n");

        //get method
        StringBuilder sb = new StringBuilder();
        sb.append("\tpublic " + javaFieldType + " get" + StringUtils.firstChar2UpperCase(columnInfo.getName()) + "() {\n");
        sb.append("\t\t return " + columnInfo.getName() + ";\n");
        sb.append("\t}\n");
        jfgs.setGetInfo(sb.toString());

        //set method
        sb.delete(0, sb.length());
        sb.append("\tpublic void set" + StringUtils.firstChar2UpperCase(columnInfo.getName()) + "(" + javaFieldType + " " + columnInfo.getName() + ") {\n");
        sb.append("\t\tthis." + columnInfo.getName() + " = " + columnInfo.getName() + ";\n");
        sb.append("\t}\n");
        jfgs.setSetInfo(sb.toString());

        return jfgs;
    }

    /**
     * 根据表信息生成java类的源代码
     *
     * @param tableInfo 表信息
     * @param convertor 数据类型转换器
     * @return javabean源代码
     */
    public static String createJavaSrc(TableInfo tableInfo, TypeConvertor convertor) {
        StringBuilder sb = new StringBuilder();

        //package
        sb.append("package " + DBManager.getConf().getPoPackage() + ";\n\n");


        //import
        sb.append("import java.sql.*;\n");
        sb.append("import java.util.*;\n\n");

        sb.append("public class " + StringUtils.firstChar2UpperCase(tableInfo.getTname()) + "{\n");

        Map<String, ColumnInfo> columnInfoMap = tableInfo.getColumns();
        for (ColumnInfo c : columnInfoMap.values()) {
            sb.append(createJavaFieldGetSetSRC(c, convertor).toString());
        }

        sb.append("}\n");

        return sb.toString();
    }

    public static void createJavaPOFile(TableInfo tableInfo, TypeConvertor convertor) {
        String src = createJavaSrc(tableInfo, convertor);

        String pathDir = DBManager.getConf().getSrcPath() + "/" +
                DBManager.getConf().getPoPackage().replace(".", "/") + "/";
        File f = new File(pathDir + StringUtils.firstChar2UpperCase(tableInfo.getTname()) + ".java");
        if (f.exists()) {
            f.delete();
        }

        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(f));
            bw.write(src);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
//        ColumnInfo columnInfo = new ColumnInfo("username", "varchar", 0);
//        JavaFieldGetSet jfgs = createJavaFieldGetSetSRC(columnInfo, new MySqlTypeConvertor());
//
//        System.out.println(jfgs);
//
//        Map<String, TableInfo> map = TableContext.tableInfoMap;
//        TableInfo t = map.get("dept");
//        createJavaPOFile(t, new MySqlTypeConvertor());
    }
}
