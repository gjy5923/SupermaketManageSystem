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
        Connection conn = JDBCUtils.getConnection();//�������ݿ�
        QueryRunner runner = new QueryRunner();//ʹ�ð���������ݿ��������DBUtils�е���
        String sql = "select * from safetymonitoring order by name desc";//�����ѯ���
        List<Safetymonitoring> gn = runner.query(conn, sql, new BeanListHandler<Safetymonitoring>(Safetymonitoring.class));//����ѯ�����ʵ����Safetymonitoring����
        conn.close();//��Ϊʹ�õ����ݿ����ӳأ��˴��Ĺر�ֻ�ǽ����ӷ������ݳ��У���û�������رգ��Եȴ�����Ӧ��ֱ��ʹ��
        return gn;
    }
    //�ϴ�ͼƬ�����ݿ�
    public static void uploadImage(String url) throws SQLException {//�������������������ʱ���ͼƬ��
        Connection conn = JDBCUtils.getConnection();//�������ݿ�
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");//����ʱ��ĸ�ʽ
        int length = url.indexOf(".");//�õ�.ǰ�������ַ��ĳ���
        String temp=url.substring(0,length);//ȡ.jpgǰ��������ַ�
        //������ɵ�����ת���ɹ涨�����ڸ�ʽ
        Date date = new Date(Long.parseLong(temp));
        String time = dateFormat.format(date);

        QueryRunner runner = new QueryRunner();//������ѯ������
        String sql = "insert ignore into safetymonitoring(name,time) values(?,?)";//�������sql���
        Object[] images=new Object[]{url,time};
        int count=runner.execute(conn, sql, images);//ִ��sql��䲢���ò���
        new Safetymonitoring().addTemp(new Safetymonitoring(url,time));
    }

    public static List<Safetymonitoring> findQuerry(String key) throws SQLException {
        Connection conn= getConnection();
        QueryRunner runner = new QueryRunner();//ʹ�ð���������ݿ��������DBUtils�е���
        String sql = "select * from safetymonitoring where "+key;//�����ѯ���
        List<Safetymonitoring> gn = runner.query(conn, sql, new BeanListHandler<Safetymonitoring>(Safetymonitoring.class));//����ѯ�����ʵ����goodsmessage����
        conn.close();//��Ϊʹ�õ����ݿ����ӳأ��˴��Ĺر�ֻ�ǽ����ӷ������ݳ��У���û�������رգ��Եȴ�����Ӧ��ֱ��ʹ��
        return gn;

    }

    //����ͳ������ͼƬ��
    public static String   countDetection() throws SQLException {
        Connection conn=JDBCUtils.getConnection();
        //�������ݿ��а�������ͼƬ��ֻ������
        String sql = "select DATE_FORMAT(time,'%y-%m-%d') days,count(*) counts from safetymonitoring  group by days order by days desc limit 7";

        QueryRunner runner = new QueryRunner();
        List<Object[]> rs = runner.query(conn, sql, new ArrayListHandler());
        conn.close();
        //�����ݱ��echart ����Ҫ�ĸ�ʽ
        String arrCount ="["+ rs.get(0)[1]+","+ rs.get(1)[1]+","+ rs.get(2)[1]+","+ rs.get(3)[1]+","+ rs.get(4)[1]+","+ rs.get(5)[1]+","+ rs.get(6)[1]+"]";
        System.out.println(arrCount);
        return arrCount;
    }

//��ȡǰ����
    public static String getDay() throws SQLException {
        Connection conn=JDBCUtils.getConnection();
        //�������ݿ��а�������ͼƬ��ֻ������
        String sql = "select DATE_FORMAT(time,'%y-%m-%d') days,count(*) counts from safetymonitoring  group by days order by days desc limit 7";
        QueryRunner runner = new QueryRunner();
        List<Object[]> rs = runner.query(conn, sql, new ArrayListHandler());
        conn.close();
        //�����ݱ��echart ����Ҫ�ĸ�ʽ
        String day ="['"+ rs.get(0)[0]+"','"+ rs.get(1)[0]+"','"+ rs.get(2)[0]+"','"+ rs.get(3)[0]+"','"+ rs.get(4)[0]+"','"+ rs.get(5)[0]+"','"+ rs.get(6)[0]+"']";
        System.out.println(day);
        return day;
    }

    public static void main(String[] args) throws SQLException {
        ImageDAO.getDay();
    }
}
