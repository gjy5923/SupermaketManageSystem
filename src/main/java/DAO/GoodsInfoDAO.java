package DAO;

import Utils.DataFormat;
import Utils.JDBCUtils;
import bean.Goodsmessage;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static Utils.JDBCUtils.getConnection;

public class GoodsInfoDAO {
     public static String y=" goodsNum,name,type.type,purchasePrice,salePrice,date,period,photo";
    public GoodsInfoDAO() {
    }

    //统计商品总量
    public static Object totalGoods() throws SQLException {
        Connection conn = JDBCUtils.getConnection();
        String sql = "select count(goodsNum) from goodsmessage";//定义查询语句
        QueryRunner runner = new QueryRunner();//使用阿帕奇的数据库操作工具DBUtils中的类
        Object count = runner.query(conn,sql,new ScalarHandler<Object>());

        conn.close();//因为使用的数据库连接池，此处的关闭只是将连接返回数据池中，并没有真正关闭，以等待其它应用直接使用
        return DataFormat.dataFormat(count);
    }

    //查询相应编号信息
    public static List<Goodsmessage> findGoodsNum(String condition) throws SQLException {
        Connection conn= getConnection();
        QueryRunner runner = new QueryRunner();//使用阿帕奇的数据库操作工具DBUtils中的类
        String sql = "select "+ y +" from goodsmessage,type where goodsmessage.type=type.typeNum and "+condition;//定义查询语句
        List<Goodsmessage> gn = runner.query(conn, sql, new BeanListHandler<Goodsmessage>(Goodsmessage.class));//将查询结果以实体类goodsmessage返回
        conn.close();//因为使用的数据库连接池，此处的关闭只是将连接返回数据池中，并没有真正关闭，以等待其它应用直接使用
        return gn;
    }

    //查找单个商品
    public static Goodsmessage findGoodsNum2(String goodsNum) throws SQLException {
        Connection conn= getConnection();
        QueryRunner runner = new QueryRunner();//使用阿帕奇的数据库操作工具DBUtils中的类
        String sql = "select "+ y +" from goodsmessage,type where goodsmessage.type=type.typeNum and goodsNum=?";//定义查询语句
        Goodsmessage gn = runner.query(conn, sql, new BeanHandler<Goodsmessage>(Goodsmessage.class), goodsNum);//将查询结果以实体类goodsmessage返回
        conn.close();//因为使用的数据库连接池，此处的关闭只是将连接返回数据池中，并没有真正关闭，以等待其它应用直接使用
        return gn;
    }
    //删除指定编号的商品信息
    public static int delete(String goodsNum) throws SQLException {

        Connection conn = JDBCUtils.getConnection();
        String sql1 = "delete from goodsmessage where goodsNum in(" + goodsNum + ")";
        PreparedStatement pstmt = conn.prepareStatement(sql1);
        int j = pstmt.executeUpdate();
        JDBCUtils.release(pstmt, conn);//释放资源
        if (j != 0) {
            return 1;
        } else {
            return 0;
        }
    }
    //添加商品信息
    public static int addGoodsInfo(Goodsmessage goods, Connection conn) throws SQLException {
        QueryRunner runner = new QueryRunner();//创建查询运行器
        String sql = "insert ignore into goodsmessage(goodsNum,name,type,purchasePrice,salePrice,date,period,photo) values(?,?,?,?,?,?,?,?)";//设置添加sql语句
        Object[] goodstmp=new Object[]{goods.getGoodsNum(),goods.getName(),goods.getType(),goods.getPurchasePrice(),goods.getSalePrice(),goods.getDate(),goods.getPeriod(),goods.getPhoto()};
        int count=runner.execute(conn, sql, goodstmp);//执行sql语句并设置参数
        return count;
    }

    //修改指定商品编号的信息
    public static int updateGoodsInfo(Goodsmessage goods) throws SQLException {
        int count = 0;
        System.out.println(goods.toString());
        Connection conn = JDBCUtils.getConnection();
        String sql = "update goodsmessage set name=?,type=?,purchasePrice=?,salePrice=?,date=?,period=?,photo=? where goodsNum=?";
        Object[] goodstmp = new Object[]{ goods.getName(),goods.getType(), goods.getPurchasePrice(), goods.getSalePrice(), goods.getDate(), goods.getPeriod(),goods.getPhoto(),goods.getGoodsNum()};
        QueryRunner runner = new QueryRunner();
        count = runner.update(conn, sql, goodstmp);
        return count;
    }
}
