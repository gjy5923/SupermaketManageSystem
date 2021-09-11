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

    //ͳ����Ʒ����
    public static Object totalGoods() throws SQLException {
        Connection conn = JDBCUtils.getConnection();
        String sql = "select count(goodsNum) from goodsmessage";//�����ѯ���
        QueryRunner runner = new QueryRunner();//ʹ�ð���������ݿ��������DBUtils�е���
        Object count = runner.query(conn,sql,new ScalarHandler<Object>());

        conn.close();//��Ϊʹ�õ����ݿ����ӳأ��˴��Ĺر�ֻ�ǽ����ӷ������ݳ��У���û�������رգ��Եȴ�����Ӧ��ֱ��ʹ��
        return DataFormat.dataFormat(count);
    }

    //��ѯ��Ӧ�����Ϣ
    public static List<Goodsmessage> findGoodsNum(String condition) throws SQLException {
        Connection conn= getConnection();
        QueryRunner runner = new QueryRunner();//ʹ�ð���������ݿ��������DBUtils�е���
        String sql = "select "+ y +" from goodsmessage,type where goodsmessage.type=type.typeNum and "+condition;//�����ѯ���
        List<Goodsmessage> gn = runner.query(conn, sql, new BeanListHandler<Goodsmessage>(Goodsmessage.class));//����ѯ�����ʵ����goodsmessage����
        conn.close();//��Ϊʹ�õ����ݿ����ӳأ��˴��Ĺر�ֻ�ǽ����ӷ������ݳ��У���û�������رգ��Եȴ�����Ӧ��ֱ��ʹ��
        return gn;
    }

    //���ҵ�����Ʒ
    public static Goodsmessage findGoodsNum2(String goodsNum) throws SQLException {
        Connection conn= getConnection();
        QueryRunner runner = new QueryRunner();//ʹ�ð���������ݿ��������DBUtils�е���
        String sql = "select "+ y +" from goodsmessage,type where goodsmessage.type=type.typeNum and goodsNum=?";//�����ѯ���
        Goodsmessage gn = runner.query(conn, sql, new BeanHandler<Goodsmessage>(Goodsmessage.class), goodsNum);//����ѯ�����ʵ����goodsmessage����
        conn.close();//��Ϊʹ�õ����ݿ����ӳأ��˴��Ĺر�ֻ�ǽ����ӷ������ݳ��У���û�������رգ��Եȴ�����Ӧ��ֱ��ʹ��
        return gn;
    }
    //ɾ��ָ����ŵ���Ʒ��Ϣ
    public static int delete(String goodsNum) throws SQLException {

        Connection conn = JDBCUtils.getConnection();
        String sql1 = "delete from goodsmessage where goodsNum in(" + goodsNum + ")";
        PreparedStatement pstmt = conn.prepareStatement(sql1);
        int j = pstmt.executeUpdate();
        JDBCUtils.release(pstmt, conn);//�ͷ���Դ
        if (j != 0) {
            return 1;
        } else {
            return 0;
        }
    }
    //�����Ʒ��Ϣ
    public static int addGoodsInfo(Goodsmessage goods, Connection conn) throws SQLException {
        QueryRunner runner = new QueryRunner();//������ѯ������
        String sql = "insert ignore into goodsmessage(goodsNum,name,type,purchasePrice,salePrice,date,period,photo) values(?,?,?,?,?,?,?,?)";//�������sql���
        Object[] goodstmp=new Object[]{goods.getGoodsNum(),goods.getName(),goods.getType(),goods.getPurchasePrice(),goods.getSalePrice(),goods.getDate(),goods.getPeriod(),goods.getPhoto()};
        int count=runner.execute(conn, sql, goodstmp);//ִ��sql��䲢���ò���
        return count;
    }

    //�޸�ָ����Ʒ��ŵ���Ϣ
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
