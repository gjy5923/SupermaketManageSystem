package DAO;

import Utils.JDBCUtils;
import bean.User;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static Utils.JDBCUtils.getConnection;

public class LoginDAO {

    //
    public static User findByKey(User tmp) throws SQLException {
        User user=null;//要返回的实现体类
        Connection conn= getConnection();//使用自定义工具类连接数据库
        QueryRunner runner = new QueryRunner();//DBUtils工具类查询器
        String sql = "select * from user where (eno=? or username=?) and password=?";//sql查询语句,查找是否有指定用户名或学号与密码
        Object[] par = new Object[]{tmp.getEno(),tmp.getUsername(),tmp.getPassword()};//设置上述查询语句中占位符的值
        user=runner.query(conn,sql,new BeanHandler<User>(User.class),par);//执行上述查询语句，返回用户实体类
        conn.close();//关闭连接
        return user;
    }
    //查找登录的用户信息
    public static User findUser(User tmp) throws SQLException {
        User user=null;//要返回的实现体类
        Connection conn= getConnection();//使用自定义工具类连接数据库
        QueryRunner runner = new QueryRunner();//DBUtils工具类查询器
        String sql = "select * from user where eno=? or username=?";//sql查询语句,查找是否有指定用户名或学号与密码
        Object[] par = new Object[]{tmp.getEno(),tmp.getUsername()};//设置上述查询语句中占位符的值
        user=runner.query(conn,sql,new BeanHandler<User>(User.class),par);//执行上述查询语句，返回用户实体类
        conn.close();//关闭连接
        return user;
    }

    //判断职工号是否已存在
    public  static boolean findEno(String data) throws SQLException {
        Connection conn= getConnection();//使用自定义工具类连接数据库
        String sql = "select * from user where eno='"+data+"'";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        return pstmt.executeQuery().next();
    }

    //注册的用户信息添加进数据库
    public static int addUser(User user) throws SQLException {
        Connection conn = JDBCUtils.getConnection();
        QueryRunner runner = new QueryRunner();//创建查询运行器
        String sql = "insert ignore into user(eno,username,password,position) values(?,?,?,?)";//设置添加sql语句
        Object[] userstmp=new Object[]{user.getEno(),user.getUsername(),user.getPassword(),user.getPosition()};
        int count=runner.execute(conn, sql, userstmp);//执行sql语句并设置参数
        return count;
    }
    //判断用户名是否已存在
    public  static boolean findUser(String data) throws SQLException {
        Connection conn= getConnection();//使用自定义工具类连接数据库
        String sql = "select * from user where username='"+data+"'";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        boolean rs=pstmt.executeQuery().next();
        return rs;
    }
}
