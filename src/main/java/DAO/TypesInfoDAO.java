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
    //��ѯ��Ʒ����
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
    //��������������Ʒ������Ϣ
    public static List<Type> checkType(String key) throws SQLException {
        Connection conn = JDBCUtils.getConnection();
        QueryRunner runner = new QueryRunner();//ʹ�ð���������ݿ��������DBUtils�е���
        String sql = "select * from type where "+key;
        List<Type> gn = runner.query(conn, sql, new BeanListHandler<Type>(Type.class));//����ѯ�����ʵ����goodsmessage����
        conn.close();
        return gn;
    }
    //������Ʒ���
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

    //�����Ʒ������Ϣ
    public static int addTypesInfo(Type type, Connection conn) throws SQLException {
        QueryRunner runner = new QueryRunner();//������ѯ������
        String sql = "insert ignore into type(typeNum,type) values(?,?)";//�������sql���
        Object[] typestmp=new Object[]{type.getTypeNum(),type.getType()};
        int count=runner.execute(conn, sql, typestmp );//ִ��sql��䲢���ò���
        return count;
    }
    //ɾ����Ʒ����
    public static int delete(String typeNum) throws SQLException {

        Connection conn = JDBCUtils.getConnection();
        String sql1 = "delete from type where typeNum in(" + typeNum + ")";
        PreparedStatement pstmt = conn.prepareStatement(sql1);
        int j = pstmt.executeUpdate();
        JDBCUtils.release(pstmt, conn);//�ͷ���Դ
        if (j != 0) {
            return 1;
        } else {
            return 0;
        }
    }

}
