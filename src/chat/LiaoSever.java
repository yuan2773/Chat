package chat;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class LiaoSever {
    public static void main(String[] args) {
        Socket socket = null;
        BufferedReader in = null;
        BufferedWriter out = null;
        BufferedReader br = null;

        try {
            //创建服务器端套接字，指定监听端口
            ServerSocket sever = new ServerSocket(8888);
            //监听客户端的连接
            socket = sever.accept();
            //获取socket的输入输出流接受和发送信息
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            br = new BufferedReader(new InputStreamReader(System.in));
            while (true){
                //接收客户端发送的信息
                String str = in.readLine();
                System.out.println("爱谁谁谁" + str);
                String str2 = "";
                //如果客户端发送的是“over”则终止连接
                if (str.equals("over")){
                    break;
                }//否则，发送反馈信息
                str2 = br.readLine();//读到\n为止
                out.write(str2 + "\n");
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (in != null){
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (out != null){
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (br != null){
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (socket != null){
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
