package test3;

import DAO.ImageDAO;

import javax.websocket.Session;
import java.io.*;
import java.net.Socket;
import java.sql.SQLException;

public class Handler extends Thread {

    Socket socket;
    Session session;

    public Handler(Socket socket, Session session) {
        this.socket = socket;
        this.session = session;
    }

    @Override
    public void run() {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = socket.getInputStream();
            // 从客户端读取发送过来的文件名
            //生成随机图片名
            String fileName =System.currentTimeMillis()  + ".jpg";
            //图片保存路径
            String filePath = "E:\\javaProgram\\SupermarketManagementSystem\\out\\artifacts\\SupermarketManagementSystem_war_exploded\\imagesCapture\\";

            File file = new File(filePath + fileName);

            int len;
            byte[] buff = new byte[1024];

            os = new FileOutputStream(file);
            while ((len = is.read(buff)) > 0) {
                os.write(buff, 0, len);//上传图片到本地文件
                os.flush();
            }
            ImageDAO.uploadImage(fileName);//上传图片到数据库
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }  finally {
            if(is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
