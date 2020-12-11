package chat;

import com.mysql.cj.x.protobuf.MysqlxDatatypes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * 数据库操作类
 */
public class DBOperator {
    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    public DBOperator() {
        try {
            this.conn = JdbcUtils.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //注册功能
    public int addUser(User user) {
        int count = 0;
        try {
            conn.setAutoCommit(false);//开启事务

            //获取数据库操作对象
            String sql = "insert into userinform (username,password,email)values (?,?,?)";
            ps = conn.prepareStatement(sql);

            //执行sql语句
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmail());
            count = ps.executeUpdate();

            conn.commit();//提交事务
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();//回滚事务
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        }
        return count;
    }

    //登陆功能
    public int loginUser(User user) {
        int count = 0;
        try {
            conn.setAutoCommit(false);//开启事务

            //查询数据库操作对象
            String sql = "select * from userinform where username=? and password=?";

            ps = conn.prepareStatement(sql);

            //执行sql语句
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            rs = ps.executeQuery();

            if (rs.next()) {
                count = 1;
            }
            conn.commit();//提交事务
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();//回滚事务
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        }
        return count;
    }

    //找回密码功能
    public Boolean ZhmmUser(User user) {
        try {
            conn.setAutoCommit(false);//开启事务
            //查询数据库操作对象
            String sql = "select * from userinform where username=? and email=?";
            ps = conn.prepareStatement(sql);
            //执行sql语句
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            } else {
                System.out.println("用户名或邮箱输入错误，验证失败");
            }
            conn.commit();//提交事务
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();//回滚事务
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        }
        return false;
    }

    public Boolean UpdatePassword(User user) throws SQLException {
        conn.setAutoCommit(false);//开启事务

        String sql1 = "update userinform set password=? where username=? and email=?";
        ps = conn.prepareStatement(sql1);
        ps.setString(1, user.getPassword());
        ps.setString(2, user.getUsername());
        ps.setString(3, user.getEmail());

        int count = ps.executeUpdate();

        conn.commit();
        return count > 0;
    }

    //修改密码功能
    public Boolean xiugaiUser(User user){
        try {
            conn.setAutoCommit(false);//开启事务
            //查询数据库操作对象
            String sql="select * from userinform where username=? and password=?";
            ps=conn.prepareStatement(sql);
            //执行sql语句
            ps.setString(1,user.getUsername());
            ps.setString(2,user.getPassword());
            rs=ps.executeQuery();
            if (rs.next()){
                return true;
            }else {
                System.out.println("用户名或原始密码输入失败，验证失败");
            }
            conn.commit();//提交事务
        } catch (SQLException e) {
            if(conn!=null){
                try {
                    conn.rollback();//回滚事务
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        }
        return false;
    }

    public Boolean UpdatePassword2(User user) throws SQLException {
        conn.setAutoCommit(false);//开启事务

        String sql1="update userinform set password=? where username=?";
        ps=conn.prepareStatement(sql1);
        ps.setString(1,user.getPassword());
        ps.setString(2,user.getUsername());

        int count = ps.executeUpdate();

        conn.commit();
        return  count > 0;
    }

    public int zhuxiaoUser(User user) throws SQLException {
            //查询数据库操作对象
            String sql = "delete from userinform where username=? ";
            ps = conn.prepareStatement(sql);
            //执行sql语句
            ps.setString(1, user.getUsername());
            int count = ps.executeUpdate();
            return  count;
    }

}
