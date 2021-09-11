package test3;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个 WebSocket 服务器端,
 * 注解的值将被用于监听用户连接的终端访问 URL 地址,客户端可以通过这个 URL 来连接到 WebSocket 服务器端
 */
@ServerEndpoint("/socket")
public class WebSocket {
	static boolean flag=true;
	MyThread thread1=new MyThread();//初始化一个线程
	Thread thread = new Thread(thread1);
	// 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的
	private static int onlineCount = 0;
	// concurrent 包的线程安全 Set ，用来存放每个客户端对应的 WebSocket 对象
	// 若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
	private static final CopyOnWriteArraySet<WebSocket> webSocketSet = new CopyOnWriteArraySet<>();

	// 与某个客户端的连接会话，需要通过它来给客户端发送数据
	private Session session;

	/**
	 * 连接建立成功调用的方法
	 * @param session  可选的参数。session 为与某个客户端的连接会话，需要通过它来给客户端发送数据
	 */
	@OnOpen
	public void onOpen(Session session) throws IOException {
		this.session = session;

		webSocketSet.add(this);
		addOnlineCount();
		System.out.println("open...");
		thread.start();
		if (flag == true) {
			//启动图片上传的线程服务，值启动一次
			new ImageServer(session);
			flag=false;
		}

	}

	/**
	 * 连接关闭调用的方法
	 */

	@OnClose
	public void onClose(){
		thread1.stopMe();//结束线程
		webSocketSet.remove(this);
		subOnlineCount();

		System.out.println("close...");
	}

	/**
	 * 收到客户端消息后调用的方法
	 * @param message 客户端发送过来的消息
	 * @param session 可选的参数
	 */

	@OnMessage
	public void onMessage(String message, Session session) {
		try {
			System.out.println(message);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// or send single message
		// Send the first message to the client
		// session.getBasicRemote().sendText("This is the first server message");
	}
//当数据库中数据量发生变化时，发送信息
	public void onMessages(int count) {
		System.out.println("发生变化"+count);
		try {
			sendMessage("1");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 发生错误时调用
	 */

	@OnError
	public void onError(Session session, Throwable error){
		error.printStackTrace();
	}

	/**
	 * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法
	 */

	public void sendMessage(String message) throws IOException{
		for(WebSocket item: webSocketSet){
			item.session.getBasicRemote().sendText("1");
		}

	}

	public static synchronized int getOnlineCount() {
		return onlineCount;
	}

	public static synchronized void addOnlineCount() {
		WebSocket.onlineCount++;
	}

	public static synchronized void subOnlineCount() {
		WebSocket.onlineCount--;
	}
}
