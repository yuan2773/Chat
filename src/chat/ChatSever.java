package chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 聊天系统端线程主类
 @author 袁紫柔
 * @date 2020.11.16
 */
public class ChatSever {
    public static void main(String[] args) {
        try {
            //创建socket服务，并监听窗口
            ServerSocket serverSocket = new ServerSocket(8888);
            System.out.println("服务器已启动，等待客户连接");
            while (true){
                Socket socket=serverSocket.accept();
                new Thread(new Sever(socket)).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
