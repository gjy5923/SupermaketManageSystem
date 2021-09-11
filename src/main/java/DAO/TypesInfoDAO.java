package DAO;

import Utils.JDBCUtils;
import bean.Type;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TypesInfoDAO {
    public TypesInfoDAO() {
    }
    //查询商品种类
    public static List checkType() throws SQLException {
        Connection conn = JDBCUtils.getConnection();
        String sql1 = "select type from type";
        List list=new ArrayList();
        PreparedStatement pstmt = conn.prepareStatement(sql1);
        ResultSet resultSet= pstmt.executeQuery();
        while (resultSet.next()) {
            list.add(resultSet.getString(1));
        }
        return list;
    }
    //查找有条件的商品种类信息
    public static List<Type> checkType(String key) throws SQLException {
        Connection conn = JDBCUtils.getConnection();
        QueryRunner runner = new QueryRunner();//使用阿帕奇的数据库操作工具DBUtils中的类
        String sql = "select * from type where "+key;
        List<Type> gn = runner.query(conn, sql, new BeanListHandler<Type>(Type.class));//将查询结果以实体类goodsmessage返回
        conn.close();
        return gn;
    }
    //查找商品编号
    public static  String checkTypeNum(String data) throws SQLException {
        Connection conn = JDBCUtils.getConnection();
        String sql = "select typeNum from type where type='"+data+"'";
        PreparedStatement pstm = conn.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        String temp=null;
        if (resultSet.next()) {
            temp= resultSet.getString(1);
        }
        resultSet.close();
        return temp;
    }

    //添加商品种类信息
    public static int addTypesInfo(Type type, Connection conn) throws SQLException {
        QueryRunner runner = new QueryRunner();//创建查询运行器
        String sql = "insert ignore into type(typeNum,type) values(?,?)";//设置添加sql语句
        Object[] typestmp=new Object[]{type.getTypeNum(),type.getType()};
        int count=runner.execute(conn, sql, typestmp );//执行sql语句并设置参数
        return count;
    }
    //删除商品种类
    public static int delete(String typeNum) throws SQLException {

        Connection conn = JDBCUtils.getConnection();
        String sql1 = "delete from type where typeNum in(" + typeNum + ")";
        PreparedStatement pstmt = conn.prepareStatement(sql1);
        int j = pstmt.executeUpdate();
        JDBCUtils.release(pstmt, conn);//释放资源
        if (j != 0) {
            return 1;
        } else {
            return 0;
        }
    }

}
