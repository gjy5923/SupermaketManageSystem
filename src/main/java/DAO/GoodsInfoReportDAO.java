package DAO;

import Utils.DataFormat;
import Utils.JDBCUtils;
import com.alibaba.fastjson.JSON;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class GoodsInfoReportDAO {
    //�õ������������Ʒ��
    public static String countType(int n) throws SQLException {
        Connection conn = JDBCUtils.getConnection();
        String sql=null;
        String str=null;
        if (n == 1) {
            sql = "select ifnull(count(goodsmessage.type),0) from type left join goodsmessage on goodsmessage.type=type.typeNum  Group by type.type";
            QueryRunner runner = new QueryRunner();
            List<Object[]> data = runner.query(conn, sql, new ArrayListHandler());
            str = "[";
            for (Object[] i : data) {
                str+=i[0]+",";
            }
            str = str.substring(0,str.length()-1);
            str+="]";
        } else {
            sql = "select type from type";
            QueryRunner runner = new QueryRunner();
            List<Object[]> data = runner.query(conn, sql, new ArrayListHandler());
            str = "[";
            for (Object[] i : data) {
                str+="\""+i[0]+"\",";
            }
            str = str.substring(0,str.length()-1);
            str+="]";
        }


        conn.close();

        return str;
    }
    //������������
    public static String countType() throws SQLException {
        Connection conn = JDBCUtils.getConnection();
        String sql = null;
        sql = "select ifnull(count(goodsmessage.type),0)value ,type.type name from type left join goodsmessage on goodsmessage.type=type.typeNum  Group by type.type";
        QueryRunner runner = new QueryRunner();
        List<Map<String, Object>> listType = runner.query(conn, sql, new MapListHandler());
        conn.close();
        String arrTYpe = JSON.toJSONString(listType);
        return  arrTYpe;

    }
    //ÿ����Ʒ��ʮ�����µ�������
    public static String sales(String data) throws SQLException {
        Connection conn = JDBCUtils.getConnection();
        String sql = "select * from salesinformation  where typeNum='"+data+"'";
        QueryRunner runner = new QueryRunner();
        List<Object[]> arrSales = runner.query(conn, sql, new ArrayListHandler());
        String temp="[";

        for(Object[] f:arrSales){
            for (int i=1;i<=12;i++) {
                temp+=f[i]+",";

            }
        }
        temp=temp.substring(0,temp.length()-1)+"]";
        return temp;
    }

    //ͳ����������
    public static Object salesTotal() throws SQLException {
        Connection conn = JDBCUtils.getConnection();
        String sql = "SELECT sum(jan+feb+mar+apr+may+jun+jul+aug+sept+oct+nov+dece)  from salesinformation";//�����ѯ���
        QueryRunner runner = new QueryRunner();//ʹ�ð���������ݿ��������DBUtils�е���
        Object count = runner.query(conn,sql,new ScalarHandler<Object>());
        conn.close();//��Ϊʹ�õ����ݿ����ӳأ��˴��Ĺر�ֻ�ǽ����ӷ������ݳ��У���û�������رգ��Եȴ�����Ӧ��ֱ��ʹ��

        return DataFormat.dataFormat(count);
    }
}
