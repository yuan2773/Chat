package chat;

import java.io.Serializable;

/**
 * 用户实体类
 */
public class User implements Serializable {
    private static final long serialVersionUID = -90512620142259229L;

    private String username;//用户名
    private String password;//用户密码
    private String email;//用户邮箱

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
