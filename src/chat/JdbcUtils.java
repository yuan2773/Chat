package chat;

import javax.print.DocFlavor;
import java.sql.*;
import java.util.ResourceBundle;

public class JdbcUtils {
    private static String driver;
    private  static String url;
    private static  String user;
    private static String password;
    private JdbcUtils(){

    }
    static {
        ResourceBundle resourceBundle=ResourceBundle.getBundle("jdbc");
        driver=resourceBundle.getString("driver");
        url=resourceBundle.getString("url");
        user=resourceBundle.getString("user");
        password=resourceBundle.getString("password");
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    public static Connection getConnection() throws SQLException {

        Connection connection= DriverManager.getConnection(url,user,password);

        return connection;
    }
    public static void close(ResultSet rs, Statement stmt,Connection conn){
        try {
            if(rs!=null){
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if(stmt!=null){
                stmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if(conn!=null){
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
