package chat;

import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Page {
    private Page(){

    }

    /**
     * 首页
     */
    public static void homePage(){
        System.out.println("请选择系统功能（用数字表示功能）");
        System.out.println("1.注册\t2.登录\t3.找回密码\t4.退出登录");
    }
    //注册页面
    public static User registerPage(){
        User user=new User();
        Scanner input = new Scanner(System.in);

        System.out.println("请输入用户名：");
        String username=input.nextLine();

        String password;
        int pCount=0;
        do{pCount++;
        if(pCount==4){
            System.exit(0);
        }
        if(pCount>1){ System.out.println("请重新输入用户密码：");
            password=input.nextLine();}else {
            System.out.println("请输入用户密码：");
            password=input.nextLine();}

        }while (!Pattern.matches("^[a-zA-Z0-9]+$",password));

        String email;
        int eCount=0;
        do{eCount++;
            if(eCount==4){
                System.exit(0);
            }
            if(eCount>1){ System.out.println("请重新输入用户邮箱：");
                email=input.nextLine();}else {
                System.out.println("请输入用户邮箱：");
                email=input.nextLine();}

        }while (!Pattern.matches("^[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+",email));
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);


        return user;
    }
    //登陆页面
    public static User dengluPage(){
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入账号：");
        String username  = sc.nextLine();
        System.out.println("请输入密码：");
        String password = sc.nextLine();

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        return user;
    }

    public static User gaiMiPage(User user){
        int eCount=0;
        do{
            eCount++;
            if(eCount==4){
                System.exit(0);
            }
            Scanner sc = new Scanner(System.in);

            System.out.println("请输入密码：");

            String psw  = sc.nextLine();

            System.out.println("请输入再次输入密码：");

            String apsw = sc.nextLine();

            if(apsw.equals(psw))
            {
                user.setPassword(psw);
            }
        }while (user.getPassword().isEmpty());

        return user;
    }

    //找回密码页面
    public static User zhmmPage(){
        Scanner input = new Scanner(System.in);
        System.out.println("请输入你要找回用户的用户名：");
        String username = input.nextLine();
        String email;
        int eCount=0;
        do{eCount++;
            if(eCount==4){
                System.exit(0);
            }
            if(eCount>1){ System.out.println("请重新输入用户邮箱：");
                email=input.nextLine();}else {
                System.out.println("请输入用户邮箱：");
                email=input.nextLine();}

        }while (!Pattern.matches("^[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+",email));

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        return user;
    }

    //主页
    public static void zhuyePage(){
        System.out.println("欢迎进入本系统，来探索本系统仅有的功能");
        System.out.println("请选择系统功能（用数字表示功能）");
        System.out.println("1.查看在线人数\t2.聊天\t3.私聊\t4.修改密码\t5.注销账号\t6.退出");
    }

    //修改密码
    public static User xiugaiPage(){
        Scanner input = new Scanner(System.in);
        System.out.println("请输入原始密码:");
        String password = input.nextLine();
        User user = new User();
        user.setPassword(password);
        return user;
    }

    public static User gaiMiPage2(User user){
        int eCount=0;
        do{
            eCount++;
            if(eCount==4){
                break;
            }
            Scanner sc = new Scanner(System.in);
            System.out.println("请输入新的密码：");
            String psw  = sc.nextLine();
            System.out.println("请输入再次输入密码：");
            String apsw = sc.nextLine();
            if(apsw.equals(psw))
            {
                user.setPassword(psw);
                break;
            }else {
                System.out.println("两次密码不一致，请重新输入。");
            }
        }while (true);
        return user;
    }

    public static User zhuxiaoPage(){
//        System.out.println("你确定要注销此账户吗？");
//        System.out.println("1.确定\t2.返回");
        User user=new User();
        System.out.println("既然这样，江湖路远，下次再见。");

        return user;
    }


}
