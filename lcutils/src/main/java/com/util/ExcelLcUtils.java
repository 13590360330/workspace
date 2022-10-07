package com.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.hutool.poi.word.Word07Writer;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import java.awt.*;
import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExcelLcUtils {

    public static void main(String[] args) {
        List<String> row1 = CollUtil.newArrayList("aa", "bb", "cc", "dd");
        List<String> row2 = CollUtil.newArrayList("aa1", "bb1", "cc1", "dd1");
        List<String> row3 = CollUtil.newArrayList("aa2", "bb2", "cc2", "dd2");
        List<String> row4 = CollUtil.newArrayList("aa3", "bb3", "cc3", "dd3");
        List<String> row5 = CollUtil.newArrayList("aa4", "bb4", "cc4", "dd4");

        List<List<String>> rows = CollUtil.newArrayList(row1, row2, row3, row4, row5);

        createExcel("F:\\日常文件\\临时目录\\数据\\test.xlsx", "sheet1", rows, "");
        createExcel("F:\\日常文件\\临时目录\\数据\\test1.xlsx", "sheet1", rows, "测试标题");

//        ExcelReader reader = ExcelUtil.getReader("d:/aaa.xlsx");
//        List<List<Object>> readAll = reader.read();

        ExcelReader reader = ExcelUtil.getReader("F:\\日常文件\\临时目录\\数据\\test1.xlsx");
        List<Map<String, Object>> readAll = reader.readAll();

//        ExcelReader reader = ExcelUtil.getReader("d:/aaa.xlsx");
//        List<Person> all = reader.readAll(Person.class);
    }

    /**
     * </> List<Bean> 写出成Excel
     *
     * @param path
     * @param sheet
     * @param rows
     * @param headline
     */
    public static void createBean2Excel(String path, String sheet, List<? extends Object> rows, String headline) {
        ArrayList<List<String>> objects = new ArrayList<>();
        ArrayList<String> filedNameList = new ArrayList<>();
        rows.forEach(bean -> {
            ArrayList<String> strList = new ArrayList<>();
            Class aClass = bean.getClass();
            Field[] fields = aClass.getDeclaredFields();
            try {
                int size = objects.size();
                for (Field field : fields) {
                    if (size == 0) {
                        filedNameList.add(field.getName());
                    }
                    field.setAccessible(true);
                    strList.add(String.valueOf(field.get(bean)));
                }
                if (size == 0) {
                    objects.add(filedNameList);
                }
                objects.add(strList);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
        createExcel(path, sheet, objects, headline);
    }

    /**
     * </> List<List> 写出成Excel
     *
     * @param path
     * @param sheet
     * @param rows
     * @param headline
     */
    public static void createExcel(String path, String sheet, List<List<String>> rows, String headline) {

        FileUtils.deleteQuietly(new File(path));

        //getBigWriter大量数据输出,对应xlsx文件
        //getWriter数据输出，对应xls文件
        ExcelWriter writer = ExcelUtil.getBigWriter(new File(path), sheet); //
        //通过构造方法创建writer
        //ExcelWriter writer = new ExcelWriter("d:/writeTest.xls");

        //跳过当前行，既第一行，非必须，在此演示用
//        writer.passCurrentRow();

        if (rows.size() == 0) {
            return;
        }


        //标题行:
        if (StringUtils.isNotBlank(headline)) {
            //rows.get( 0 ).size() - 1  标题行的长度
            writer.merge(rows.get(0).size() - 1, headline);
        }


        //一次性写出内容，强制输出标题
        writer.write(rows, true);
        //关闭writer，释放内存
        writer.close();

    }

    /**
     * 创建word文档
     */
    public static void createWord() {
        Word07Writer writer = new Word07Writer();

        // 添加段落（标题）
        writer.addText(new Font("方正小标宋简体", Font.PLAIN, 22), "我是第一部分", "我是第二部分");
        // 添加段落（正文）
        writer.addText(new Font("宋体", Font.PLAIN, 22), "我是正文第一部分", "我是正文第二部分");
        // 写出到文件
        writer.flush(FileUtil.file("e:/wordWrite.docx"));
        // 关闭
        writer.close();
    }

}
