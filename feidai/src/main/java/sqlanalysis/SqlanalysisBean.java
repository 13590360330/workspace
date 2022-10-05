package sqlanalysis;

import cn.hutool.core.text.StrSpliter;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

import static utils.ConstantUtils.*;


/**
 * author:liuch
 * date:2022-10-01
 * desc: ((( ))) 的子查询需要格外处理
 */
@NoArgsConstructor
@Data
@ToString
public class SqlanalysisBean {
    private List<FromBean> from;
    private List<WhereBean> where;
    private List<GroupbyBean> groupby;
    private List<HavingBean> having;
    private List<SelectBean> select;
    private List<DistinctBean> distinct;
    private List<OrderbyBean> orderby;
    private String limit;
    private String offset;
    private HashMap<String, String> tableMap;

    @NoArgsConstructor
    @Data
    public static class FromBean {
        private String tablename;
        private String sql;
        private String join;
        private String on;

        public FromBean(String str) {

        }
    }

    @NoArgsConstructor
    @Data
    public static class WhereBean {
        private String condition;

        public WhereBean(String condition) {
            this.condition = condition;
        }
    }

    @NoArgsConstructor
    @Data
    public static class GroupbyBean {
        private String column;

        public GroupbyBean(String column) {
            this.column = column;
        }
    }

    @NoArgsConstructor
    @Data
    public static class HavingBean {
        private String condition;

        public HavingBean(String condition) {
            this.condition = condition;
        }
    }

    @NoArgsConstructor
    @Data
    @ToString
    public class SelectBean {
        private String tableAlise;
        private String column;
        private SqlanalysisBean sqlanalysisBean;
        private String columnAlisa;
        private String sql;
        private String fromTable;

        public SelectBean(String str) {
            if (str.contains( SYMBOL5 ) && str.contains( SYMBOL6 )) {
                this.sqlanalysisBean = initSqlanalysisBean( str );
                //todo 子查询别名 要注意（（（ ）））
//                this.columnAlisa =
                return;
            }

            String[] split = str.split( BLANK );
            if (split.length == 1) {
                //t1.column
                splitColumn( split[0] );
            } else if (split.length == 2) {
                //t1.column column
                splitColumn( split[0] );
                this.columnAlisa = split[1];
            } else if (split.length == 3) {
                //t1.column as column
                splitColumn( split[0] );
                this.columnAlisa = split[2];
            }
        }

        //切分 t1.column
        private void splitColumn(String str) {
            if (str.contains( SYMBOL4 )) {
                this.tableAlise = str.split( SYMBOL4 )[0];
                this.column = str.split( SYMBOL4 )[1];
                this.fromTable = tableMap.get( str.split( SYMBOL4 )[0] );
            } else {
                this.column = str;
                //todo 补充fromTable的判断
            }
        }
    }

    @NoArgsConstructor
    @Data
    public static class DistinctBean {
        private String column;
    }

    @NoArgsConstructor
    @Data
    public static class OrderbyBean {
        private String condition;

        public OrderbyBean(String condition) {
            this.condition = condition;
        }
    }

    public static SqlanalysisBean initSqlanalysisBean(String sql) {

        SqlanalysisBean sqlanalysisBean = new SqlanalysisBean();
        if (sql.contains( LIMIT )) {
            sqlanalysisBean.setLimit( sql.split( LIMIT )[1] );
            sql = sql.split( LIMIT )[0];
        } else if (sql.contains( LIMIT.toLowerCase() )) {
            sqlanalysisBean.setLimit( sql.split( LIMIT.toLowerCase() )[1] );
            sql = sql.split( LIMIT )[0];
        }

        if (sql.contains( OFFSET )) {
            sqlanalysisBean.setOffset( sql.split( OFFSET )[1] );
            sql = sql.split( OFFSET )[0];
        } else if (sql.contains( OFFSET.toLowerCase() )) {
            sqlanalysisBean.setOffset( sql.split( OFFSET.toLowerCase() )[1] );
            sql = sql.split( OFFSET )[0];
        }

        if (sql.contains( ORDERBY )) {
            sqlanalysisBean.setOrderby( sql.split( ORDERBY )[1] );
            sql = sql.split( ORDERBY )[0];
        } else if (sql.contains( ORDERBY.toLowerCase() )) {
            sqlanalysisBean.setOrderby( sql.split( ORDERBY.toLowerCase() )[1] );
            sql = sql.split( ORDERBY )[0];
        }

        if (sql.contains( HAVING )) {
            sqlanalysisBean.setHaving( sql.split( HAVING )[1] );
            sql = sql.split( HAVING )[0];
        } else if (sql.contains( HAVING.toLowerCase() )) {
            sqlanalysisBean.setHaving( sql.split( HAVING.toLowerCase() )[1] );
            sql = sql.split( HAVING )[0];
        }

        if (sql.contains( GROUPBY )) {
            sqlanalysisBean.setGroupby( sql.split( GROUPBY )[1] );
            sql = sql.split( GROUPBY )[0];
        } else if (sql.contains( GROUPBY.toLowerCase() )) {
            sqlanalysisBean.setGroupby( sql.split( GROUPBY.toLowerCase() )[1] );
            sql = sql.split( GROUPBY )[0];
        }

        if (sql.contains( WHERE )) {
            sqlanalysisBean.setWhere( sql.split( WHERE )[1] );
            sql = sql.split( WHERE )[0];
        } else if (sql.contains( WHERE.toLowerCase() )) {
            sqlanalysisBean.setWhere( sql.split( WHERE.toLowerCase() )[1] );
            sql = sql.split( WHERE )[0];
        }

        if (sql.contains( FROM )) {
            sqlanalysisBean.setFrom( sql.split( FROM )[1] );
            sql = sql.split( FROM )[0];
        } else if (sql.contains( FROM.toLowerCase() )) {
            sqlanalysisBean.setFrom( sql.split( FROM.toLowerCase() )[1] );
            sql = sql.split( FROM )[0];
        }

        if (sql.contains( SELECT )) {
            sqlanalysisBean.setSelect( sql.split( SELECT )[1] );
        } else if (sql.contains( SELECT.toLowerCase() )) {
            sqlanalysisBean.setSelect( sql.split( SELECT .toLowerCase() )[1] );
        }

        return sqlanalysisBean;
    }

    private void setSelect(String selectStr) {
        this.select = StrSpliter.split( selectStr, SYMBOL3, 0, true, true ).stream()
                .map( str -> new SelectBean( str ) ).collect( Collectors.toList() );
    }

    private void setFrom(String fromStr) {
        this.from = StrSpliter.split( fromStr, AND, 0, true, true ).stream()
                .map( str -> new FromBean( str ) ).collect( Collectors.toList() );
    }

    private void setWhere(String whereStr) {
        this.where = StrSpliter.split( whereStr, AND, 0, true, true ).stream()
                .map( str -> new WhereBean( str ) ).collect( Collectors.toList() );
    }

    private void setGroupby(String groupbyStr) {
        this.groupby = StrSpliter.split( groupbyStr, SYMBOL3, 0, true, true ).stream()
                .map( str -> new GroupbyBean( str ) ).collect( Collectors.toList() );
    }

    private void setHaving(String havingStr) {
        this.having = StrSpliter.split( havingStr, AND, 0, true, true ).stream()
                .map( str -> new HavingBean( str ) ).collect( Collectors.toList() );
    }

    private void setOrderby(String orderbyStr) {
        this.orderby = StrSpliter.split( orderbyStr, SYMBOL3, 0, true, true ).stream()
                .map( str -> new OrderbyBean( str ) ).collect( Collectors.toList() );
    }


    public static void main(String[] args) throws IOException {
        String s = FileUtils.readFileToString( new File( "D:\\workspace\\feidai\\src\\main\\resources\\example.sql" ), "utf-8" );
        s = s.replaceAll( SYMBOL1, BLANK ).replaceAll( SYMBOL2, BLANK );
        SqlanalysisBean sqlanalysisBean = initSqlanalysisBean( s );
    }
}