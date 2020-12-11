package chat;


import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class LiaoClient {
    public static void main(String[] args) {
        Socket socket = null;
        BufferedReader in = null;
        BufferedWriter out = null;
        BufferedReader wt = null;

        try {
                //创建socket对象，指定服务器端的ip与端口
                socket = new Socket(InetAddress.getLocalHost(),8888);
                //获取socket的输入输出流接受和发送消息
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                wt = new BufferedReader(new InputStreamReader(System.in));
                while (true){
                //发送信息
                String str = wt.readLine();
                out.write(str + "\n");
                out.flush();
                //如果输入的信息为“over”则终止连接
                if (str.equals("over")){
                    break;
                }//否则，接受并输出服务器端信息
                System.out.println("22" + in.readLine());
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
                if (wt != null){
                    wt.close();
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
