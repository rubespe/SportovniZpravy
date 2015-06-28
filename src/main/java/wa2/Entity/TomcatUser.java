package wa2.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tomcat_user")
public class TomcatUser {

    protected String user_name;
    protected String user_role;

    public TomcatUser(String user_name, String user_role) {
        this.user_name = user_name;
        this.user_role = user_role;
    }



    public TomcatUser() {

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
    public String getUser_role() {
        return user_role;
    }

    public void setUser_role(String user_role) {
        this.user_role = user_role;
    }
}
