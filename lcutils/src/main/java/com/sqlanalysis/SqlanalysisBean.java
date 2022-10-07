package com.sqlanalysis;

import cn.hutool.core.text.StrSpliter;
import cn.hutool.core.util.StrUtil;
import com.util.ExcelLcUtils;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.util.ConstantUtils.*;


/**
 * author:liuch
 * date:2022-10-01
 * desc:
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
    private HashMap<String, Object> tabWithAliseMap = new HashMap<String, Object>();
    private HashMap<String, String> subqueryMap = new HashMap<String, String>();

    @NoArgsConstructor
    @Data
    @ToString
    public class FromBean {
        private String tablename;
        private SqlanalysisBean sqlanalysisBean;
        private String tableAlise;
        private String joinType;
        private String onCondition;

        public FromBean(String str) {
            //TAB2 T2 ON T1.ID=T2.ID LEFT
            if (StrUtil.containsIgnoreCase(str, ON)) {
                List<String> split = StrSpliter.splitIgnoreCase(this.funcRepalce(str), ON, 0, true, true);
                funcOn(split.get(0));
                this.onCondition = split.get(1);
            } else {
                funcOn(this.funcRepalce(str));
            }
        }

        private String funcRepalce(String str) {
            String strTrim1 = str.trim().toUpperCase();
            if (strTrim1.endsWith(LEFT1)) {
                this.joinType = LEFT;
                str = str.substring(0, str.length() - 4);
            } else if (strTrim1.endsWith(RIGHT1)) {
                this.joinType = RIGHT;
                str = str.substring(0, str.length() - 5);
            } else if (strTrim1.endsWith(INNER1)) {
                this.joinType = INNER;
                str = str.substring(0, str.length() - 5);
            }
            return str;
        }

        private void funcOn(String str) {
            String[] s = str.split(" ");
            if (s[0].startsWith("(") && s[0].endsWith(")")) {
                //处理 (select .......) T2
                this.sqlanalysisBean = new SqlanalysisBean();
                //去掉子查询左右两边的括号
                this.sqlanalysisBean.initSqlanalysisBean(subqueryMap.get(s[0].substring(1, s[0].length() - 1)));
                if (s.length == 2) {
                    this.tableAlise = s[1];
                    tabWithAliseMap.put(this.tableAlise, this.sqlanalysisBean);
                } else {
                    throw new RuntimeException(String.format("sql语句错误"));
                }
            } else {
                //处理 -- TAB2 T2
                if (s.length == 2) {
                    this.tablename = str.split(" ")[0];
                    this.tableAlise = str.split(" ")[1];
                    tabWithAliseMap.put(this.tableAlise, this.tablename);
                } else {
                    this.tablename = str.split(" ")[0];
                    tabWithAliseMap.put(this.tablename, this.tablename);
                }
            }
        }
    }

    @NoArgsConstructor
    @Data
    @ToString
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
        private String func;
        private String columnAlisa;
        private String fromTable;

        public SelectBean(String str) {
            if (str.contains(SYMBOL5) && str.contains(SYMBOL6)) {
                if (str.startsWith(SYMBOL5)) {
                } else {
                    //func()  字段上有函数的处理
                    String[] split = str.split(BLANK);
                    if (split.length == 2) {
                        this.columnAlisa = split[1];
                    } else if (split.length == 3) {
                        this.columnAlisa = split[2];
                    }
                    String[] split1 = split[0].split(SPLITSYMBOL5);
                    this.setFunc(split1[0]);
                    splitColumn(subqueryMap.get(split1[1].substring(0, split1[1].length() - 1)));
                    return;
                }
            }

            String[] split = str.split(BLANK);
            if (split.length == 1) {
                //t1.column
                splitColumn(split[0]);
            } else if (split.length == 2) {
                //t1.column column
                splitColumn(split[0]);
                this.columnAlisa = split[1];
            } else if (split.length == 3) {
                //t1.column as column
                splitColumn(split[0]);
                this.columnAlisa = split[2];
            }
        }

        //切分 t1.column
        private void splitColumn(String str) {
            if (str.contains(SYMBOL7)) {
                String[] split = str.split(SPLITSYMBOL4);
                this.tableAlise = split[0];
                this.column = split[1];
                if (tabWithAliseMap.get(this.tableAlise) instanceof SqlanalysisBean) {
                    //对t1.column as column1， t1是子查询的处理
                    this.fromTable = ((SqlanalysisBean) tabWithAliseMap.get(this.tableAlise)).getSelect().stream()
                            .filter(selectBean -> selectBean.getColumnAlisa().equalsIgnoreCase(this.column))
                            .map(selectBean -> selectBean.getFromTable())
                            .collect(Collectors.joining(BLANK));
                } else {
                    this.fromTable = (String) tabWithAliseMap.get(this.tableAlise);
                }
            } else {
                // 对字段为(select sum(....) from table) as column1子查询的处理
                String[] split = str.split(BLANK);
                if (split[0].startsWith("(") && split[0].endsWith(")")) {
                    this.column = subqueryMap.get(split[0].substring(1, split[0].length() - 1));
                }
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

    public void initSqlanalysisBean(String sql) {
        sql = this.replaceSubquery(sql);
        if (StrUtil.containsIgnoreCase(sql, LIMIT)) {
            this.setLimit(StrSpliter.splitIgnoreCase(sql, LIMIT, 0, true, true).get(1));
            sql = StrSpliter.splitIgnoreCase(sql, LIMIT, 0, true, true).get(0);
        }

        if (StrUtil.containsIgnoreCase(sql, OFFSET)) {
            this.setOffset(StrSpliter.splitIgnoreCase(sql, OFFSET, 0, true, true).get(1));
            sql = StrSpliter.splitIgnoreCase(sql, OFFSET, 0, true, true).get(0);
        }

        if (StrUtil.containsIgnoreCase(sql, ORDERBY)) {
            this.setOrderby(StrSpliter.splitIgnoreCase(sql, ORDERBY, 0, true, true).get(1));
            sql = StrSpliter.splitIgnoreCase(sql, ORDERBY, 0, true, true).get(0);
        }

        if (StrUtil.containsIgnoreCase(sql, HAVING)) {
            this.setHaving(StrSpliter.splitIgnoreCase(sql, HAVING, 0, true, true).get(1));
            sql = StrSpliter.splitIgnoreCase(sql, HAVING, 0, true, true).get(0);
        }

        if (StrUtil.containsIgnoreCase(sql, GROUPBY)) {
            this.setGroupby(StrSpliter.splitIgnoreCase(sql, GROUPBY, 0, true, true).get(1));
            sql = StrSpliter.splitIgnoreCase(sql, GROUPBY, 0, true, true).get(0);
        }

        if (StrUtil.containsIgnoreCase(sql, WHERE)) {
            this.setWhere(StrSpliter.splitIgnoreCase(sql, WHERE, 0, true, true).get(1));
            sql = StrSpliter.splitIgnoreCase(sql, WHERE, 0, true, true).get(0);
        }

        if (StrUtil.containsIgnoreCase(sql, FROM)) {
            this.setFrom(StrSpliter.splitIgnoreCase(sql, FROM, 0, true, true).get(1));
            sql = StrSpliter.splitIgnoreCase(sql, FROM, 0, true, true).get(0);
        }

        if (StrUtil.containsIgnoreCase(sql, SELECT)) {
            this.setSelect(StrSpliter.splitIgnoreCase(sql, SELECT, 0, true, true).get(0));
        }
    }

    private void setSelect(String selectStr) {
        this.select = StrSpliter.splitIgnoreCase(selectStr, SYMBOL3, 0, true, true).stream()
                .map(str -> new SelectBean(str)).collect(Collectors.toList());
    }

    private void setFrom(String fromStr) {
        this.from = StrSpliter.splitIgnoreCase(fromStr, JOIN1, 0, true, true).stream()
                .map(str -> new FromBean(str)).collect(Collectors.toList());
    }

    private void setWhere(String whereStr) {
        this.where = StrSpliter.splitIgnoreCase(whereStr, AND, 0, true, true).stream()
                .map(str -> new WhereBean(str)).collect(Collectors.toList());
    }

    private void setGroupby(String groupbyStr) {
        this.groupby = StrSpliter.splitIgnoreCase(groupbyStr, SYMBOL3, 0, true, true).stream()
                .map(str -> new GroupbyBean(str)).collect(Collectors.toList());
    }

    private void setHaving(String havingStr) {
        this.having = StrSpliter.splitIgnoreCase(havingStr, AND, 0, true, true).stream()
                .map(str -> new HavingBean(str)).collect(Collectors.toList());
    }

    private void setOrderby(String orderbyStr) {
        this.orderby = StrSpliter.splitIgnoreCase(orderbyStr, SYMBOL3, 0, true, true).stream()
                .map(str -> new OrderbyBean(str)).collect(Collectors.toList());
    }

    private String replaceSubquery(String content) {
        char[] chars = content.toCharArray();
        int i = 0;
        ArrayList<Character> characters = new ArrayList<>();
        for (char c : chars) {
            if (c == SYMBOL8) {
                i++;
            } else if (c == SYMBOL9) {
                i--;
            }

            if (i > 0) {
                characters.add(c);
            } else if (i == 0) {
                if (characters.size() > 0) {
                    characters.remove(0);
                    String join = StringUtils.join(characters, "");
                    String uuid = String.valueOf(UUID.randomUUID());
                    this.subqueryMap.put(uuid, join);
                    content = StringUtils.replaceOnce(content, join, uuid);
                    characters.clear();
                }
            }
        }
        return content;
    }

    /**
     * 获取sql字段血缘关系
     * @param sql
     * @return
     */
   public static List<SelectBean> get(String sql){
       sql = sql.replaceAll(SYMBOL1, BLANK).replaceAll(SYMBOL2, BLANK);
       SqlanalysisBean sqlanalysisBean1 = new SqlanalysisBean();
       sqlanalysisBean1.initSqlanalysisBean(sql);
       return sqlanalysisBean1.getSelect();
   }


   @Test
    public void test() throws IOException {
        String s = FileUtils.readFileToString(new File("D:\\BIGDATA\\gitProject\\workspace\\feidai\\src\\main\\resources\\example.sql"), "utf-8");
        ExcelLcUtils.createBean2Excel("D:\\Users\\Administrator\\Desktop\\刘成\\临时目录\\20221007\\example.xlsx", "sheet1", get(s), "example.sql");
    }
}