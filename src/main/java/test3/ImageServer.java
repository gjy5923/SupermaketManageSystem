package test3;

import javax.websocket.Session;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ImageServer implements Runnable{

    private Session session;
    private ServerSocket serverSocket;

    Boolean isRunning = true;

    public ImageServer() throws IOException {
        startRunning();
    }

    public ImageServer(Session session) throws IOException {
        this.session = session;
        startRunning();
    }

    private void startRunning() throws IOException {
        serverSocket = new ServerSocket(9000);
        // 启动线程
        new Thread(this).start();
    }

    @Override
    public void run() {
        System.out.println("---------- Server StartUp ----------");
        int count = 0;

        while (isRunning) {
            try {
                // 堵塞式，接受客户端请求
                Socket socket = serverSocket.accept();
                // 启动子线程，保存土图片
                new Handler(socket, session).start();

                count++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println(count);
    }
}
