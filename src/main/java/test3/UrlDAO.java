package test3;

import Utils.JDBCUtils;
import bean.Safetymonitoring;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UrlDAO {

    public UrlDAO() {

    }

    // 查询检测图片数量
    public static int selectCount() {

        List<Safetymonitoring> monitorList = null;
        try {
            Connection conn = JDBCUtils.getConnection();
            QueryRunner qr = new QueryRunner();
            String sql = "select * from safetymonitoring";
            monitorList = qr.query(conn,sql,new BeanListHandler<Safetymonitoring>(Safetymonitoring.class));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return  monitorList.size();
    }
}

