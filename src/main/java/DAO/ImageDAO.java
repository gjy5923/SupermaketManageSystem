package DAO;

import Utils.JDBCUtils;
import bean.Safetymonitoring;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import static Utils.JDBCUtils.getConnection;

public class ImageDAO {
    public static List<Safetymonitoring> imageList() throws SQLException {
        Connection conn = JDBCUtils.getConnection();//连接数据库
        QueryRunner runner = new QueryRunner();//使用阿帕奇的数据库操作工具DBUtils中的类
        String sql = "select * from safetymonitoring order by name desc";//定义查询语句
        List<Safetymonitoring> gn = runner.query(conn, sql, new BeanListHandler<Safetymonitoring>(Safetymonitoring.class));//将查询结果以实体类Safetymonitoring返回
        conn.close();//因为使用的数据库连接池，此处的关闭只是将连接返回数据池中，并没有真正关闭，以等待其它应用直接使用
        return gn;
    }
    //上传图片到数据库
    public static void uploadImage(String url) throws SQLException {//传过来的是随机产生的时间撮图片名
        Connection conn = JDBCUtils.getConnection();//连接数据库
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");//定义时间的格式
        int length = url.indexOf(".");//得到.前面所有字符的长度
        String temp=url.substring(0,length);//取.jpg前面的所有字符
        //随机生成的数据转换成规定的日期格式
        Date date = new Date(Long.parseLong(temp));
        String time = dateFormat.format(date);

        QueryRunner runner = new QueryRunner();//创建查询运行器
        String sql = "insert ignore into safetymonitoring(name,time) values(?,?)";//设置添加sql语句
        Object[] images=new Object[]{url,time};
        int count=runner.execute(conn, sql, images);//执行sql语句并设置参数
        new Safetymonitoring().addTemp(new Safetymonitoring(url,time));
    }

    public static List<Safetymonitoring> findQuerry(String key) throws SQLException {
        Connection conn= getConnection();
        QueryRunner runner = new QueryRunner();//使用阿帕奇的数据库操作工具DBUtils中的类
        String sql = "select * from safetymonitoring where "+key;//定义查询语句
        List<Safetymonitoring> gn = runner.query(conn, sql, new BeanListHandler<Safetymonitoring>(Safetymonitoring.class));//将查询结果以实体类goodsmessage返回
        conn.close();//因为使用的数据库连接池，此处的关闭只是将连接返回数据池中，并没有真正关闭，以等待其它应用直接使用
        return gn;

    }

    //按天统计入侵图片数
    public static String   countDetection() throws SQLException {
        Connection conn=JDBCUtils.getConnection();
        //查找数据库中按天分组的图片且只近七天
        String sql = "select DATE_FORMAT(time,'%y-%m-%d') days,count(*) counts from safetymonitoring  group by days order by days desc limit 7";

        QueryRunner runner = new QueryRunner();
        List<Object[]> rs = runner.query(conn, sql, new ArrayListHandler());
        conn.close();
        //把数据变成echart 所需要的格式
        String arrCount ="["+ rs.get(0)[1]+","+ rs.get(1)[1]+","+ rs.get(2)[1]+","+ rs.get(3)[1]+","+ rs.get(4)[1]+","+ rs.get(5)[1]+","+ rs.get(6)[1]+"]";
        System.out.println(arrCount);
        return arrCount;
    }

//获取前七天
    public static String getDay() throws SQLException {
        Connection conn=JDBCUtils.getConnection();
        //查找数据库中按天分组的图片且只近七天
        String sql = "select DATE_FORMAT(time,'%y-%m-%d') days,count(*) counts from safetymonitoring  group by days order by days desc limit 7";
        QueryRunner runner = new QueryRunner();
        List<Object[]> rs = runner.query(conn, sql, new ArrayListHandler());
        conn.close();
        //把数据变成echart 所需要的格式
        String day ="['"+ rs.get(0)[0]+"','"+ rs.get(1)[0]+"','"+ rs.get(2)[0]+"','"+ rs.get(3)[0]+"','"+ rs.get(4)[0]+"','"+ rs.get(5)[0]+"','"+ rs.get(6)[0]+"']";
        System.out.println(day);
        return day;
    }

    public static void main(String[] args) throws SQLException {
        ImageDAO.getDay();
    }
}
