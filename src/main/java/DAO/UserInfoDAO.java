package DAO;

import Utils.DataFormat;
import Utils.JDBCUtils;
import bean.User;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static Utils.JDBCUtils.getConnection;

public class UserInfoDAO {

    //统计用户总量
    public static Object totalUsers() throws SQLException {
        Connection conn = JDBCUtils.getConnection();
        String sql = "select count(eno) from user";//定义查询语句
        QueryRunner runner = new QueryRunner();//使用阿帕奇的数据库操作工具JDBUtils中的类
        Object count = runner.query(conn,sql,new ScalarHandler<Object>());

        conn.close();//因为使用的数据库连接池，此处的关闭只是将连接返回数据池中，并没有真正关闭，以等待其它应用直接使用

        return DataFormat.dataFormat(count);
    }
    //查询相应编号信息
    public static List<User> findEno(String condition) throws SQLException {
        Connection conn= getConnection();
        QueryRunner runner = new QueryRunner();//使用阿帕奇的数据库操作工具DBUtils中的类
        String sql = "select * from user where "+condition;//定义查询语句
        List<User> gn = runner.query(conn, sql, new BeanListHandler<User>(User.class));//将查询结果以实体类User返回
        conn.close();//因为使用的数据库连接池，此处的关闭只是将连接返回数据池中，并没有真正关闭，以等待其它应用直接使用
        return gn;
    }

    //查找单个用户
    public static User findEno2(String eno) throws SQLException {
        Connection conn= getConnection();
        QueryRunner runner = new QueryRunner();//使用阿帕奇的数据库操作工具DBUtils中的类
        String sql = "select * from user where  eno=?";//定义查询语句
        User gn = runner.query(conn, sql, new BeanHandler<User>(User.class), eno);//将查询结果以实体类User返回
        conn.close();//因为使用的数据库连接池，此处的关闭只是将连接返回数据池中，并没有真正关闭，以等待其它应用直接使用
        return gn;
    }
    //删除指定编号的职工信息
    public static int delete(String eno) throws SQLException {

        Connection conn = JDBCUtils.getConnection();
        String sql1 = "delete from user where eno in(" + eno + ")";
        PreparedStatement pstmt = conn.prepareStatement(sql1);
        int j = pstmt.executeUpdate();
        JDBCUtils.release(pstmt, conn);//释放资源
        if (j != 0) {
            return 1;
        } else {
            return 0;
        }
    }

    //修改指定职工的信息
    public static int updateUsersInfo(User user) throws SQLException {
        int count = 0;
        Connection conn = JDBCUtils.getConnection();
        String sql = "update user set username=?,password=?,position=? where eno=?";
        Object[] goodstmp = new Object[]{ user.getUsername(),user.getPassword(),user.getPosition(),user.getEno()};
        QueryRunner runner = new QueryRunner();
        count = runner.update(conn, sql, goodstmp);
        return count;
    }

}

