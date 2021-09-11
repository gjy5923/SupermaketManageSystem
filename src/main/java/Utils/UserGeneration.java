package Utils;
;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class UserGeneration {
    static {
        DruidDataSourceFactory factory = new DruidDataSourceFactory(); //实例化Druid工厂类
        Properties p = new Properties();
        InputStream in = UserGeneration.class.getClassLoader().getResourceAsStream("Druid.properties");// 使用ClassLoader加载properties配置文件生成对应的输入流
        Connection conn = null;
        try {
            p.load(in);// 使用properties对象加载输入流
            DataSource dataSource = factory.createDataSource(p);//创建数据源
            conn = dataSource.getConnection();//创建数据库连接

            String sql = " insert IGNORE into goodsmessage (goodsNum,name,type,purchasePrice,salePrice,date,period,photo) values(?,?,?,?,?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            int i;
            for (i = 0; i < 1000; i++) {
                pstmt.setString(1, RandomValue.getGoodsNum());
                pstmt.setString(2, RandomValue.getChineseName());
                pstmt.setString(3, RandomValue.getType());
                Float temp = Float.valueOf(RandomValue.getNum(1, 20));
                pstmt.setString(4,String.valueOf(temp) );
                pstmt.setString(5, String.valueOf(temp+10));
                pstmt.setString(6,RandomValue.randomDate());
                pstmt.setString(7,String.valueOf(RandomValue.getNum(2,3)));
                int index = RandomValue.getNum(1,4);
                pstmt.setString(8,"photo"+index+".jpg");
                pstmt.executeUpdate();

            }
//            String sql = " insert IGNORE into user (eno,username,password,position) values(?,?,?,?)";
//            PreparedStatement pstmt = conn.prepareStatement(sql);
//
//            int i;
//            for (i = 0; i < 20; i++) {
//                pstmt.setString(1, RandomValue.getXh());
//                pstmt.setString(2,null);
//                pstmt.setString(3, "staff");
//                pstmt.setString(4, "123456");
//                pstmt.executeUpdate();
//
//            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new UserGeneration();
    }

}
