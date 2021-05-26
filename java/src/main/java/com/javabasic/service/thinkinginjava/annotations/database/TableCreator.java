package com.javabasic.service.thinkinginjava.annotations.database;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO 注解的作用:在源代码级别保存类文件的元数据(备注信息或者默认数据),经过编译后成文类文件的Annotation信息,在使用时,使用反射或者mirror API可以获取这些信息
 * <p>
 * java为类保存元数据(默认数据)的可靠方式就是注解了
 * <p>
 * TODO 使用注解生成建表语句
 */
public class TableCreator {
    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println( "arguments: annotated classes" );
            System.exit( 0 );
        }
        for (String className : args) {
            //args 是类文件路径
            Class<?> cl = Class.forName( className );
            //获取cl类文件中的DBTable注解
            DBTable dbTable = cl.getAnnotation( DBTable.class );
            //输入的类名要是寻找DBTable注解的检查
            if (dbTable == null) {
                System.out.println(
                        "No DBTable annotations in class " + className );
                continue;
            }
            String name = dbTable.name();
            if (name.length() < 1) {
                //如果dbTable.name被设为""时,就用输入的类文件名
                name = cl.getName().toUpperCase();
            }
            List<String> columnDefs = new ArrayList<String>();
            for (Field field : cl.getDeclaredFields()) {
                String columnName = null;
                Annotation[] anns = field.getDeclaredAnnotations();
                if (anns.length < 1)
                    continue;
                if (anns[0] instanceof SQLIntger) {
                    SQLIntger sInt = (SQLIntger) anns[0];
                    if (sInt.name().length() < 1)
                        //如果注解为name()被赋予"",则取字段名本身
                        columnName = field.getName().toUpperCase();
                    else
                        columnName = sInt.name();
                    columnDefs.add( columnName + " INT" +
                            getConstraints( sInt.constraints() ) );
                }
                if (anns[0] instanceof SQLString) {
                    SQLString sString = (SQLString) anns[0];
                    if (sString.name().length() < 1)
                        //如果注解为name()被赋予"",则取字段名本身
                        columnName = field.getName().toUpperCase();
                    else
                        columnName = sString.name();
                    columnDefs.add( columnName + " VARCHAR(" + sString.value() + ")" +
                            getConstraints( sString.constraints() ) );
                }
                StringBuilder createCommand = new StringBuilder( "CREATE TABLE " + name + "(" );
                for (String columnDef : columnDefs)
                    createCommand.append( "\n " + columnDef + " ," );
                String tableCreate = createCommand.substring( 0, createCommand.length() - 1 ) + ");";
                System.out.println( "Table Creation SQL for" +
                        className + " is :\n" + tableCreate );
            }
        }
    }

    private static String getConstraints(Constraints con) {
        String constraints = "";
        if (!con.allowNull())
            constraints += " NOT NULL";
        if (con.primaryKey())
            constraints += " PRIMARY KEY";
        if (con.unique())
            constraints += " UNIQUE";
        return constraints;
    }
}
