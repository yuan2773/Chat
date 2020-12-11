package chat;


import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.SocketHandler;

/**
 * 聊天系统客户端
 *
 * @author 袁紫柔
 * @date 2020.11.16
 */
public class Sever implements Runnable {
    private Socket socket;
    private static Map<String, Socket> userxinxi = new ConcurrentHashMap<>();

    /**
     * 系统主页功能
     *
     * @param socket
     */

    public Sever(Socket socket) {
        this.socket = socket;
    }
    @Override
    public void run() {
        //系统首页功能
        try {
            boolean uu = homeFuntion(socket);
            if (uu) {
                systemFunction(socket);
            }
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        //系统主页功能
    }

    private void systemFunction(Socket socket) {
        try {
            //获取流对象
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            String s1 = null;
            String username = null;
            Set<Map.Entry<String, Socket>> set = userxinxi.entrySet();
            for (Map.Entry<String, Socket> entry : set) {
                if (entry.getValue().equals(socket)) {
                    username = entry.getKey();
                    break;
                }
            }
            quit:
            while (true) {
                //读取键盘获得得数据
                s1 = (String) in.readLine();
                if (s1 != null) {
                    switch (s1) {
                        case "1"://查看在线人数
                            System.out.println(userxinxi.keySet().toString());
                            //取值，打印用户名
                            out.println(userxinxi.keySet().toString());
                            break;
                        case "2"://群聊功能
                            break;

                        case "3"://私聊功能
                            //获取数据
                            String s = in.readLine();
                            //用空格分割用户名和用户发送的内容
                            String[] s2 = s.split(" ");
                            String user_name = s2[0];
                            String content = s2[1];
                            Socket socket1 = userxinxi.get(user_name);
                            sendMassage(socket1, content);
                            break;
                        case "4"://修改密码功能

                            try {
                                User user = new User();
                                String password  = in.readLine();
                                user.setUsername(username);
                                user.setPassword(password);
                                DBOperator dbOperator1 = new DBOperator();
                                String registerMessage1 = dbOperator1.xiugaiUser(user) ? "验证成功" : "验证失败";
                                out.println(registerMessage1);
                                String psw = in.readLine();
                                user.setPassword(psw);
                                user.setUsername(username);
                                if (dbOperator1.UpdatePassword2(user))
                                    out.println("修改密码成功");
                                else
                                    out.println("修改密码失败");
                            } catch (IOException | SQLException e) {
                                e.printStackTrace();
                            } finally {
                                s1 = null;
                            }
                            break;
                        case "5"://注销账号
                            User user1 = new User();
                            user1.setUsername(username);
                            DBOperator dbOperator = new DBOperator();
                            int count = dbOperator.zhuxiaoUser(user1);
                            //注销结果
                            String registerMessage = count == 1 ? "注销成功" : "注销失败";
                            out.println(registerMessage);//将用户注册的结果返回
                            userxinxi.remove(username);
                            break quit;
                        case "6"://退出系统主页返回到首页
                            //输出提示信息
                            System.out.println("用户" + s1 + "已下线");

                            System.exit(0);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 服务器端的首页功能
     *
     * @param socket
     */
    private boolean homeFuntion(Socket socket) throws IOException, ClassNotFoundException, SQLException {
        //获取流对象
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        boolean uu = false;
        String s1 = null;
        lable1:
        while (true) {
            s1 = (String) in.readObject();//接收客户端发过来的菜单选项
            if (s1 != null) {
                switch (s1) {
                    case "1":
                        try {
                            //接收客户端发送过来的数据
                            User user = (User) in.readObject();
                            //将用户信息添加到数据库
                            DBOperator dbOperator = new DBOperator();
                            int count = dbOperator.addUser(user);
                            //注册结果
                            String registerMessage = count == 1 ? "注册成功" : "注册失败";
                            out.writeObject(registerMessage);//将用户注册的结果返回
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        } finally {
                            s1 = null;
                        }
                        break;
                    case "2":
                        //读取客户发过来的数据
                        User user1 = (User) in.readObject();
                        //将用户信息添加到数据库
                        DBOperator dbOperator = new DBOperator();
                        //执行DBO操作，并返回结果
                        int count = dbOperator.loginUser(user1);
                        //返回结果
                        String registerMessage1 = count == 1 ? "登陆成功" : "登陆失败，账号或密码错误";
                        //如果登陆成功打印上面的信息
                        if (count == 1) {
                            out.writeObject(registerMessage1);
                            //将用户名放在socket
                            userxinxi.put(user1.getUsername(), socket);
                            //发给系统主页，利用uu跳转到主页
                            uu = true;
                            break lable1;
                        }
                        out.writeObject(registerMessage1);
                        break;

                    case "3":
                        User user;//读取客户发过来的数据
                        user = (User) in.readObject();
                        //将用户信息添加到数据库
                        DBOperator dbOperator1 = new DBOperator();
                        //执行DBP里的操作并返回结果
                        String registerMessage2 = dbOperator1.ZhmmUser(user) ? "请开始验证" : "验证失败";
                        //打印结果
                        out.writeObject(registerMessage2);
                        //读取新的密码
                        String psw = (String) in.readObject();
                        //把新密码发给数据库
                        user.setPassword(psw);
                        if (dbOperator1.UpdatePassword(user))
                            out.writeObject("修改密码成功");
                        else
                            out.writeObject("修改密码失败");
                        break;
                    case "4":
                        System.exit(0);
                }
            }
        }
        return uu;
    }

    public void sendMassage(Socket socket, String content) {
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
