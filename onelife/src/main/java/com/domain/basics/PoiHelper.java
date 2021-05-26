package com.domain.basics;

import org.apache.poi.ss.usermodel.Cell;

public class PoiHelper {

    /**
     * 以下变量大写为对应Excel列编号
     * 以下变量小写为对应映射中的变量
     */
    /**
     * 源系统名
     */
    public static final int SOURCE_SYS_INDEX = 0;
    public static final String source_sys = "${source_sys}";

    /***
     * 源表名
     */
    public static final int SOURCE_TABLE_NAME_INDEX = 1;
    public static final String source_tableName = "${source_tableName}";
    public static final String source_asName = "${source_asName}"; //源表别名

    /***
     * 源表中文名
     */
    public static final int SOURCE_TABLE_CN_NAME_INDEX = 2;
    public static final String source_tablecnName = " ";
    /***
     * 源字段名称
     */
    public static final int SOURCE_FIELD_NAME_INDEX = 3;
    public static final String source_fieldName = "";
    /***
     * 源字段中文名称
     */
    public static final int SOURCE_FIELD_CN_NAME_INDEX = 4;
    public static final String source_fieldCnName = "${source_fieldCnName}";
    /**
     * 源字段类型
     */
    public static final int SOURCE_FIELD_TYPE_INDEX = 5;
    public static final String source_fieldType = "${source_fieldType}";


    /***
     * 映射规则
     */
    public static final int MAPPING_EXPRESSION_INDEX = 6;
    public static final String mapping_expression = "${mapping_expression}";
    /***
     * 映射规则说明(中文描述)
     */
    public static final int MAPPING_COMMENT_INDEX = 7;
    public static final String mapping_comment = "${mapping_comment}";

    /**
     * JOIN语句
     */
    public static final String joinCondition = "";

    /***
     * JOIN方式
     */
    public static final int JOIN_TYPE_INDEX = 8;
    public static final String joinType = "${joinType}";

    /***
     * 次源表系统名
     */
    public static final int JOIN_TABLE_SYS = 9;
    public static final String second_join_table_sys = "${second_join_table_sys}";

    /***
     * 次源表名
     */
    public static final int JOIN_TABLE_NAME_INDEX = 10;
    public static final String second_source_tableName = "${second_source_tableName}";
    /***
     *次源表别名
     */
    public static final int JOIN_TABLE_AS_NAME_INDEX = 11;
    public static final String second_source_asName = "${second_source_asName}";
    /***
     * ON条件
     */
    public static final int JOIN_CONDITION_INDEX = 12;
    public static final String onCondition = "${onCondition}";
    /***
     * ON条件说明
     */
    public static final int onCnCondition_index = 13;
    public static final String onCnCondition = "${onCnCondition}";
    /***
     *过滤条件
     */
    public static final int WHERE_CONDITION_INDEX = 14;
    public static final String where = "${where}";
    /***
     *过滤条件说明
     */
    public static final int FILTERCNCONDITION_INDEX = 15;
    public static final String filterCnCondition = "${filterCnCondition}";

    /***
     * 目标系统名
     */
    public static final int TARGET_SYS_INDEX = 16;
    public static final String target_sys = "${target_sys}";
    /***
     * 目标表名
     */
    public static final int TARGET_TABLE_NAME_INDEX = 17;
    public static final String target_table_name = "${target_table_name}";
    /***
     * 目标表中文名
     */
    public static final int TARGET_TABLE_CN_NAME_INDEX = 18;
    public static final String target_table_cn_name = "${target_table_cn_name}";
    /**
     * 目标字段顺序
     */
    public static final int TARGET_FIELD_NUM_INDEX = 19;
    public static final String target_index = "${target_index}";
    /**
     * 目标字段名称
     */
    public static final int TARGET_FIELD_NAME_INDEX = 20;
    public static final String target_fieldName = "";
    /**
     * 目标字段中文名称
     */
    public static final int TARGET_FIELD_CN_NAME_INDEX = 21;
    public static final String target_fieldCnName = "${target_fieldCnName}";

    /**
     * 目标字段类型
     */
    public static final int TARGET_FIELD_TYPE_INDEX = 22;
    public static final String target_fileType = "${target_fileType}";

    /**
     * 主键
     */
    public static final int ISKEY_INDEX = 23;
    public static final String iskey = "${iskey}";


    public static final String SOURCE_SCHEMA = "lpuat3";
    public static final String TARGET_SCHEMA = "lpuat2";
    public static final String source_schema = "lpuat3";
    public static final String target_schema = "lpuat2";

    public static String cellValueToString(Cell cell) {
        String result = "";

        switch (cell.getCellType().name()) {
            case "NUMERIC":
                result = cell.getNumericCellValue() + "";
                break;
            case "STRING":
                result = cell.getStringCellValue();
                break;
            default:
                break;
        }

        return result;
    }
}
