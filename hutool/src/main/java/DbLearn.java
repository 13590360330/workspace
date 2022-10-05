import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.db.ds.DSFactory;
import cn.hutool.setting.Setting;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;


/**
 * author:liuch
 * date：2022-10-02
 * desc: 数据库操作
 */
public class DbLearn {

    public static void main(String[] args) throws SQLException {

        //查询
        List<Entity> result1 = Db.use().query( "select * from test.user where age < ?", 3 );

        //模糊查询
        List<Entity> result2 = Db.use().query( "select * from test.user where name like ?", "王%" );

        //新增
        Db.use().execute( "insert into test.user values (?, ?, ?)", "张三", 17, 1 );

        //删除
        Db.use().execute( "delete from test.user where name = ?", "张三" );

        //更新
        Db.use().execute( "update test.user set age = ? where name = ?", 3, "张三" );

        //重点
//        Db.use().executeBatch( "insert into test.user values (?, ?, ?)", list);
        //利用这个可以不用依赖配置文件

        //具体的配置参数请参阅Druid官方文档
//        DruidDataSource ds2 = new DruidDataSource();
//        ds2.setUrl("jdbc:mysql://localhost:3306/dbName");
//        ds2.setUsername("root");
//        ds2.setPassword("123456");

//        Db.use( ds2, "com.mysql.jdbc.Driver");
//        Db.use( ds2);

        //
        //自定义数据库Setting，更多实用请参阅Hutool-Setting章节
        Setting setting = new Setting( "otherPath/other.setting" );
        //获取指定配置，第二个参数为分组，用于多数据源，无分组情况下传null
        // 注意此处DSFactory需要复用或者关闭
        DataSource ds = DSFactory.create( setting ).getDataSource();

        //2. 在同一配置文件中使用分组隔离不同的数据源配置
        /**
         * [group_db1]
         * url = jdbc:mysql://<host>:<port>/<database_name>
         * username = 用户名
         * password = 密码
         *
         * [group_db2]
         * url = jdbc:mysql://<host2>:<port>/<database_name>
         * username = 用户名
         * password = 密码
         */

        DataSource ds1 = DSFactory.get("group_db1");
        DataSource ds2 = DSFactory.get("group_db2");

    }

}
