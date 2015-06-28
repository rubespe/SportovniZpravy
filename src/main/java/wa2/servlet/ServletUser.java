package wa2.servlet;



import wa2.Entity.User;
import wa2.hibernate.HibernateUser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "ServletUser")
public class ServletUser extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json");
// Get the printwriter object from response to write the required json object to the output stream
        PrintWriter out = response.getWriter();
// Assuming your json object is **jsonObject**, perform the following, it will return your json object

        String login = request.getParameter("username");
        String password = request.getParameter("password");

        Logger.getLogger("MyLogger").log(Level.SEVERE, login);
        Logger.getLogger("MyLogger").log(Level.SEVERE, password);

        User u = HibernateUser.getUserByLogin(login);

        //Logger.getLogger("MyLogger").log(Level.SEVERE, u.toString());

        if(u == null){
            out.print("User doesnt exist");
            out.flush();
        }else{
            out.print(u);
            out.flush();
        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("username");
        String password = request.getParameter("password");

        Logger.getLogger("MyLogger").log(Level.SEVERE, login);
        Logger.getLogger("MyLogger").log(Level.SEVERE, password);

        User u = HibernateUser.getUserByLogin(login);
        PrintWriter out = response.getWriter();
        if(u != null){
            out.write("Login already in use");
            out.close();
            return;
        }else{
            u = new User(login, password);
            HibernateUser.saveUser(u);

        }

        out.write("done");
        out.close();
    }
}
