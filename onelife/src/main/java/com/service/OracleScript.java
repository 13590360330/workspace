package com.service;

import com.dao.AnalysisExcel;
import com.domain.Block;
import com.domain.SqlContent;

import java.util.ArrayList;
import java.util.List;

public class OracleScript {

    public static void main(String[] args) {
        //指定本地SVN副本根目录
        String rootPath = "E:\\日常文件\\知识点整理\\CDH\\DW\\project\\中国人寿";
        int startRow = 1;

        AnalysisExcel analysisExcel = new AnalysisExcel();
        //读取Mapping
        //品种
        ArrayList<Block> xlsxContent = analysisExcel.readXlsx( rootPath
                        + "\\每日任务\\20200818\\品种mapping.xlsx"
                , "股票", startRow );

        List<SqlContent> sqlContent = analysisExcel.createSqlContent( xlsxContent );

//        //依据mapping内容生成Script
//        CreateOracleScript createOralceScript = new CreateOracleScript();
//        createOralceScript.createScript( sqlContent );

    }
}
