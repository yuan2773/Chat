package chat;

import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * 聊天系统客户端
 *
 * @author 袁紫柔
 * @date 2020.11.16
 */
public class Client {
    public static void main(String[] args) {
        Socket socket = null;
        try {
            //创建socket服务，并指定服务器的ip和端口port
            socket = new Socket("127.0.0.1", 8888);

            //首页功能
            int fanhui = homeFunction(socket);

            //系统主页功能
            if (fanhui == 1) {
                systemFunction(socket);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 系统主页功能
     */
    private static void systemFunction(Socket socket) throws IOException {

        Scanner input = new Scanner(System.in);
        //获取对象
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String s1;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        String msg="";
                        while ((msg=in.readLine())!=null){
                            System.out.println(msg);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        quit:
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Page.zhuyePage();
            s1 = input.next();
            out.println(s1);
            //1.查看在线人数	2.聊天	3.私聊	4.修改密码	5.退出
            switch (s1) {
                case "1"://查看在线人数
                    break ;

                case "2"://群聊功能
                    break ;

                case "3"://私聊功能
                    Scanner scanner = new Scanner(System.in);
                    System.out.println("输入格式（用户名 内容）");
                    String s = scanner.nextLine();
                    out.println(s);
                    break ;

                case "4"://修改密码功能
                    User user0 = Page.xiugaiPage();
                    out.println(user0.getPassword());
                    try {
                        String registerMassage1 =  in.readLine();
                        if (registerMassage1.equals("验证成功")) {
                            user0 = Page.gaiMiPage2(user0);
                            out.println(user0.getPassword());
                            registerMassage1 = in.readLine();
                            System.out.println(registerMassage1);
                        } else
                            System.out.println(registerMassage1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "5"://注销账号
                    break quit;
                case "6"://退出系统主页返回到首页
                    System.exit(0);

            }
        }
    }

    /**
     * 首页功能
     */
    private static int homeFunction(Socket socket) throws IOException, ClassNotFoundException {
        Scanner input = new Scanner(System.in);
        //获取对象
        int flag = 0;
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        String s1;//菜单选项
        lable1:
        while (true) {
            Page.homePage();//首页页面
            s1 = input.next();//获取键盘录入的菜单选项
            out.writeObject(s1);//将菜单选项发送给服务器端
            switch (s1) {
                case "1":
                    //客户注册功能
                    User user = Page.registerPage();//进入注册界面
                    out.writeObject(user);//将用户数据发送给服务器
                    try {
                        //客户端接受服务端返回的注册结果
                        String registerMassage = (String) in.readObject();
                        //打印注册结果
                        System.out.println(registerMassage);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
                case "2":
                    //客户登录功能
                    User user1 = Page.dengluPage();
                    out.writeObject(user1);

                    String registerMassage1 = (String) in.readObject();
                    if (registerMassage1.equals("登陆成功")) {
                        flag = 1;
                        break lable1;
                    }
                    System.out.println(registerMassage1);

                    break;
                case "3":
                    //客户端找回密码功能
                    User user0 = Page.zhmmPage();
                    out.writeObject(user0);

                    String registerMassage3 = (String) in.readObject();
                    if (registerMassage3.equals("请开始验证")) {
                        user0 = Page.gaiMiPage(user0);
                        out.writeObject(user0.getPassword());
                        registerMassage3 = (String) in.readObject();
                        System.out.println(registerMassage3);
                    } else
                        System.out.println(registerMassage3);

                    break;
                case "4":
                    //客户端退出功能
                    System.exit(0);
            }
        }
        return flag;
    }
}
