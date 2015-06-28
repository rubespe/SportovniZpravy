package wa2.Entity;

import javax.persistence.*;

/**
 * Created by Petr on 15.6.2015.
 */

@Entity
@Table(name = "users")
public class User {

    private String user_name;
    private String user_pass;
    private String user_role;

    public User(String user_name, String user_pass) {
        this.user_name = user_name;
        this.user_pass = user_pass;
        this.user_role = "user";
    }

    public User() {

    }

    @Id
    @Column
    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }


    @Column
    public String getUser_pass() {
        return user_pass;
    }

    public void setUser_pass(String user_pass) {
        this.user_pass = user_pass;
    }


    @Column
    public String getUser_role() {
        return user_role;
    }

    public void setUser_role(String user_role) {
        this.user_role = user_role;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_name='" + user_name + '\'' +
                ", user_pass='" + user_pass + '\'' +
                ", user_role='" + user_role + '\'' +
                '}';
    }
}
