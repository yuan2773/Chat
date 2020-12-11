package chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class LiaoduoClient {
    public static void main(String[] args) throws IOException {
        //接收服务器
        Socket server = new Socket("localhost", 8888);
        //启动线程
        new Thread(new Send(server)).start();
        new Thread(new Receive(server)).start();
        System.out.println("请输入昵称");
    }

    //接收消息
    static class Receive implements Runnable {
        private Socket server = null;
        private boolean flag = true;
        DataInputStream serverR = null;
        //构造函数
        public Receive(Socket server) {
            this.server = server;
            try {
                this.serverR = new DataInputStream(server.getInputStream());
            } catch (Exception e) {
                e.printStackTrace();
                flag = false;
            }
        }

        @Override
        public void run() {
            while (flag) {
                System.out.println(receive());
            }
        }

        private String receive() {
            String msg = null;
            try {
                msg = serverR.readUTF();
            } catch (IOException e) {
                //e.printStackTrace();
                System.out.println("接收信息失败！");
                flag = false;
            }
            return msg;
        }

    }

    //发送信息
    static class Send implements Runnable {
        private Socket server = null;
        private DataOutputStream serverW = null;
        private boolean flag = true;

        public Send(Socket server) {
            this.server = server;
            try {
                serverW = new DataOutputStream(server.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            while (flag) {
                send();
            }
        }

        private void send() {
            Scanner scanner = new Scanner(System.in);
            String msg = scanner.next();
            try {
                serverW.writeUTF(msg);
                serverW.flush();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("发送信息失败！");
                flag = false;
            }
        }
    }
}
