package test2;

import javax.websocket.RemoteEndpoint.Basic;
import javax.websocket.Session;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;


public class SendImageToWeb {

    private Session session;

    private FileInputStream fis;

    public SendImageToWeb() {

    }

    public SendImageToWeb(Session session, FileInputStream fis) {
        this.session = session;
        this.fis = fis;
    }

    public void pushImage() {
        try {
            FileInputStream fs;

            if (this.fis == null) {
                fs = new FileInputStream("E:\\javaProgram\\SupermarketManagementSystem\\out\\artifacts\\SupermarketManagementSystem_war_exploded\\imagesCapture\\1625018121745.jpg");
            } else {
                fs = this.fis;
            }

            // 获取指定文件的长度并用它来创建一个可以存放内容的字节数组
            byte[] content = new byte[fs.available()];

            // 将图片内容读入到字节数组
            int len = fs.read(content);

            // 使用刚才的字节数组创建 ByteBuffer 对象
            ByteBuffer byteBuffer = ByteBuffer.wrap(content, 0, len);
            Basic basic = session.getBasicRemote();

            // 发送 ByteBuffer 对象到客户端
            basic.sendBinary(byteBuffer);
            // 关闭文件流对象
            fs.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
