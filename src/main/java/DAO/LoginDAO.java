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
        User user=null;//Ҫ���ص�ʵ������
        Connection conn= getConnection();//ʹ���Զ��幤�����������ݿ�
        QueryRunner runner = new QueryRunner();//DBUtils�������ѯ��
        String sql = "select * from user where (eno=? or username=?) and password=?";//sql��ѯ���,�����Ƿ���ָ���û�����ѧ��������
        Object[] par = new Object[]{tmp.getEno(),tmp.getUsername(),tmp.getPassword()};//����������ѯ�����ռλ����ֵ
        user=runner.query(conn,sql,new BeanHandler<User>(User.class),par);//ִ��������ѯ��䣬�����û�ʵ����
        conn.close();//�ر�����
        return user;
    }
    //���ҵ�¼���û���Ϣ
    public static User findUser(User tmp) throws SQLException {
        User user=null;//Ҫ���ص�ʵ������
        Connection conn= getConnection();//ʹ���Զ��幤�����������ݿ�
        QueryRunner runner = new QueryRunner();//DBUtils�������ѯ��
        String sql = "select * from user where eno=? or username=?";//sql��ѯ���,�����Ƿ���ָ���û�����ѧ��������
        Object[] par = new Object[]{tmp.getEno(),tmp.getUsername()};//����������ѯ�����ռλ����ֵ
        user=runner.query(conn,sql,new BeanHandler<User>(User.class),par);//ִ��������ѯ��䣬�����û�ʵ����
        conn.close();//�ر�����
        return user;
    }

    //�ж�ְ�����Ƿ��Ѵ���
    public  static boolean findEno(String data) throws SQLException {
        Connection conn= getConnection();//ʹ���Զ��幤�����������ݿ�
        String sql = "select * from user where eno='"+data+"'";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        return pstmt.executeQuery().next();
    }

    //ע����û���Ϣ��ӽ����ݿ�
    public static int addUser(User user) throws SQLException {
        Connection conn = JDBCUtils.getConnection();
        QueryRunner runner = new QueryRunner();//������ѯ������
        String sql = "insert ignore into user(eno,username,password,position) values(?,?,?,?)";//�������sql���
        Object[] userstmp=new Object[]{user.getEno(),user.getUsername(),user.getPassword(),user.getPosition()};
        int count=runner.execute(conn, sql, userstmp);//ִ��sql��䲢���ò���
        return count;
    }
    //�ж��û����Ƿ��Ѵ���
    public  static boolean findUser(String data) throws SQLException {
        Connection conn= getConnection();//ʹ���Զ��幤�����������ݿ�
        String sql = "select * from user where username='"+data+"'";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        boolean rs=pstmt.executeQuery().next();
        return rs;
    }
}
