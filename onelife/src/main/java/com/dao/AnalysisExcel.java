package com.dao;

import com.domain.*;
import com.domain.basics.*;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

public class AnalysisExcel {

    /**
     * 获取excel内容
     *
     * @param filePath
     * @param sheetName
     * @param startRow
     * @return
     */
    public ArrayList<Block> readXlsx(String filePath, String sheetName, int startRow) {
        System.out.println( filePath );
        ArrayList<Block> allBlock = null;
        try (InputStream inp = FileUtils.openInputStream( new File( filePath ) )) {
            //WorkbookFactory方法解析字节流inp,获取对应excel的信息
            Workbook wb = WorkbookFactory.create( inp );
            Sheet sheet = wb.getSheet( sheetName );
            allBlock = getAll( startRow, sheet );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allBlock;
    }

    public ArrayList<Block> getAll(int startRow, Sheet sheet) {
        Block block;
        ArrayList<Block> allBlockSet = new ArrayList<Block>();

        int prevStartRow = 1;
        int prevEndRow = -1;

        for (int row = startRow; row <= sheet.getLastRowNum(); row++) {
            String tgtTableName = "";
            int tgtFieldPos;
            Cell tableCell = sheet.getRow( row ).getCell( PoiHelper.TARGET_TABLE_NAME_INDEX ); //目标表名
            Cell posCell = sheet.getRow( row ).getCell( PoiHelper.TARGET_FIELD_NUM_INDEX );  //目标字段顺序

            if (null != tableCell && null != posCell) {
                tgtTableName = tableCell.getStringCellValue();
                tgtFieldPos = (int) posCell.getNumericCellValue();
            } else {
                // 如果是空单元格则跳过
                continue;
            }

            // 如果是空数据则跳过
            if (null == tgtTableName || tgtTableName.trim().isEmpty()) {
                continue;
            }

            //判断当前行是新起点
            if (1 == tgtFieldPos && prevEndRow != -1) {
                //生成上一个targettable对应的block
                block = this.getBlock( sheet, prevStartRow, prevEndRow );
                allBlockSet.add( block );
                prevStartRow = row;
            } else {
                prevEndRow = row;
            }
        }
//        //处理最后一个Block
        block = this.getBlock( sheet, prevStartRow, prevEndRow );
        allBlockSet.add( block );
        return allBlockSet;
    }

    /**
     * 将excel内容存入Block
     *
     * @param sheet
     * @param startrow
     * @param endrow
     * @return
     */
    private Block getBlock(Sheet sheet, int startrow, int endrow) {
        Source source;
        Join join;
        Where where;
        Column column;
        Block block = new Block();
        ArrayList<Source> sources = new ArrayList<Source>();
        ArrayList<Join> joins = new ArrayList<Join>();
        ArrayList<Where> wheres = new ArrayList<Where>();
        ArrayList<Column> columns = new ArrayList<Column>();
        for (int row = startrow; row <= endrow; row++) {
            Cell posCell = sheet.getRow( row ).getCell( PoiHelper.TARGET_FIELD_NUM_INDEX, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK );                   //目标字段顺序
            Cell target_tablename = sheet.getRow( row ).getCell( PoiHelper.TARGET_TABLE_NAME_INDEX, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK );         //目标表名
            source = new Source();
            source.setSys( sheet.getRow( row ).getCell( PoiHelper.SOURCE_SYS_INDEX, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK ).getStringCellValue() );//源系统名
            source.setTablename( sheet.getRow( row ).getCell( PoiHelper.SOURCE_TABLE_NAME_INDEX, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK ).getStringCellValue() );
            source.setTablecnname( sheet.getRow( row ).getCell( PoiHelper.SOURCE_TABLE_CN_NAME_INDEX, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK ).getStringCellValue() );//源表中文名
            source.setColumn( sheet.getRow( row ).getCell( PoiHelper.SOURCE_FIELD_NAME_INDEX, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK ).getStringCellValue() );//源字段名称
            source.setIskey( posCell.getNumericCellValue() );
            source.setColumncnname( sheet.getRow( row ).getCell( PoiHelper.SOURCE_FIELD_CN_NAME_INDEX, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK ).getStringCellValue() );//源字段中文名称
            source.setColumntype( sheet.getRow( row ).getCell( PoiHelper.SOURCE_FIELD_TYPE_INDEX, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK ).getStringCellValue() );//源字段类型
            source.setColumnMapping( sheet.getRow( row ).getCell( PoiHelper.MAPPING_EXPRESSION_INDEX, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK ).getStringCellValue() );//映射规则
            source.setColumnMappingdesc( sheet.getRow( row ).getCell( PoiHelper.MAPPING_COMMENT_INDEX, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK ).getStringCellValue() );//映射规则说明(中文描述)
            sources.add( source );
            join = new Join();
            join.setJoinType( sheet.getRow( row ).getCell( PoiHelper.JOIN_TYPE_INDEX, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK ).getStringCellValue() ); //JOIN方式
            join.setSys( sheet.getRow( row ).getCell( PoiHelper.JOIN_TABLE_SYS, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK ).getStringCellValue() ); //JOIN次元表系统名
            join.setAsName( sheet.getRow( row ).getCell( PoiHelper.JOIN_TABLE_AS_NAME_INDEX, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK ).getStringCellValue() ); //次源表别名
            join.setSecSourceTable( sheet.getRow( row ).getCell( PoiHelper.JOIN_TABLE_NAME_INDEX, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK ).getStringCellValue() );//次源表名
            join.setOnCondition( sheet.getRow( row ).getCell( PoiHelper.JOIN_CONDITION_INDEX, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK ).getStringCellValue() );//ON条件
            join.setOnCnCondition( sheet.getRow( row ).getCell( PoiHelper.onCnCondition_index, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK ).getStringCellValue() );//ON条件说明
            joins.add( join );
            where = new Where();
            where.setFilterCondition( sheet.getRow( row ).getCell( PoiHelper.WHERE_CONDITION_INDEX, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK ).getStringCellValue() );//过滤条件
            where.setFilterCnCondition( sheet.getRow( row ).getCell( PoiHelper.FILTERCNCONDITION_INDEX, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK ).getStringCellValue() );//过滤条件说明
            wheres.add( where );
            column = new Column();
            column.setColumnsequence( posCell.getNumericCellValue() );
            column.setColumnname( sheet.getRow( row ).getCell( PoiHelper.TARGET_FIELD_NAME_INDEX, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK ).getStringCellValue() );//目标字段名称
            column.setColumncnname( sheet.getRow( row ).getCell( PoiHelper.TARGET_FIELD_CN_NAME_INDEX, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK ).getStringCellValue() );//目标字段中文名称
            column.setColumntype( sheet.getRow( row ).getCell( PoiHelper.TARGET_FIELD_TYPE_INDEX, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK ).getStringCellValue() );//目标字段类型
            column.setIskey( sheet.getRow( row ).getCell( PoiHelper.ISKEY_INDEX, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK ).getStringCellValue() );//是否主键
            columns.add( column );
            if (row == endrow) {
                block.set_sourceTable( new SourceTable( target_tablename.getStringCellValue(), sources ) );
                block.set_fromCondition( new fromCondition( target_tablename.getStringCellValue(), joins ) );
                block.set_whereCondition( new WhereCondition( target_tablename.getStringCellValue(), wheres ) );
                block.set_targetTable( new TargetTable( sheet.getRow( row )
                        .getCell( PoiHelper.TARGET_SYS_INDEX, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK ).getStringCellValue(),
                        target_tablename.getStringCellValue(),
                        sheet.getRow( row ).getCell( PoiHelper.TARGET_TABLE_CN_NAME_INDEX, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK ).getStringCellValue(),
                        columns ) );
            }
        }
        return block;
    }

    /**
     * 将sql的各个部分存入SqlContent
     *
     * @param xlsxContent
     * @return
     */
    public List<SqlContent> createSqlContent(ArrayList<Block> xlsxContent) {
        List<SqlContent> sqlContentList = xlsxContent.stream().map( x -> {
            SqlContent sqlContent = new SqlContent();
            //目标表名
            String sqltargetTable = x.get_targetTable().getTablename();

            //拼接select字段
            List<String> selectCondition = x.get_sourceTable().getSources().stream().map( z -> z.getColumnMapping() )
                    .filter( z -> !z.equals( null ) ).collect( Collectors.toList() );
            String sqlselectCondition = String.join( "\n,", selectCondition );

            //from条件
            List<String> joinCondition = x.get_fromCondition().getJoins().stream().map( z -> {
                String join = null;
                if (!(z.getJoinType().isEmpty() || z.getJoinType().matches( "^[\\s]+$" ))) {
                    join = z.getJoinType() + " " + z.getSys() + "." + z.getSecSourceTable() + " " + z.getAsName()
                            + "\n  on " + z.getOnCondition();
                } else {
                    join = z.getSys() + "." + z.getSecSourceTable() + " " + z.getAsName();
                }
                return join;
            } ).filter( z -> !(z.isEmpty() || z.matches( "^[\\s.]+$" )) ).collect( Collectors.toList() );
            String sqlFromCondition = String.join( "\n", joinCondition );

            //where条件
            List<String> whereCondition = x.get_whereCondition().getWheres().stream().map( z -> {
                String where = z.getFilterCondition();
                return where;
            } ).filter( z -> !(z.isEmpty() || z.matches( "^[-\\s]+$" )) ).collect( Collectors.toList() );
            String sqlWhereCondition = String.join( "\n  ", whereCondition );

            //装载
            sqlContent.setTargetTablename( sqltargetTable );
            sqlContent.setColumn( x.get_targetTable().getColumns() );
            sqlContent.setSelectCondition( sqlselectCondition );
            sqlContent.setFromCondition( sqlFromCondition );
            sqlContent.setWhereCondition( sqlWhereCondition );

            return sqlContent;
        } ).collect( Collectors.toList() );

        return sqlContentList;
    }
}
