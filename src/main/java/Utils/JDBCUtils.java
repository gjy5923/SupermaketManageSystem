package Utils; /**
 * @Auther: GU
 * @Date: 2021/4/21 10:14
 * @Description:
 */
import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 *@ClassName Utils.JDBCUtils
 *@Author GJY
 *@Version 1.0
 **/
public class JDBCUtils {

    //创建数据库连接
    public static Connection getConnection(){
        DruidDataSourceFactory factory = new DruidDataSourceFactory();//实例化Druid工厂
        Properties pro = new Properties();
        InputStream in = JDBCUtils.class.getClassLoader().getResourceAsStream("Druid.properties");// 使用ClassLoader加载properties配置文件生成对应的输入流
        Connection conn = null;
        try {
//            conn = DriverManager.getConnection(url, username, password);
            pro.load(in);//使用properties对象加载输入流
            DataSource dataSource = factory.createDataSource(pro);//创建数据源
            conn = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
    //释放资源
    public static void release(Statement stmt,Connection conn){
        try {
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void release(ResultSet rs,Statement stmt) {
        try {
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void release(ResultSet rs,Statement stmt,Connection conn) {
        try {
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
