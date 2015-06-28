package wa2.servlet;

import org.json.JSONArray;
import org.json.JSONObject;
import wa2.Entity.Sport;
import wa2.Entity.Udalost;
import wa2.hibernate.HibernateSport;
import wa2.hibernate.HibernateUdalost;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by Petr on 28. 6. 2015.
 */
@WebServlet(name = "ServletUdalostiSport")
public class ServletUdalostiSport extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("sportId");
        System.out.println(name);

        response.setContentType("application/json");

        PrintWriter out = response.getWriter();

        response.flushBuffer();

        List<Udalost> udalosti = HibernateUdalost.getAllMessagesForSport(Integer.parseInt(name));
        JSONArray list = new JSONArray();

        for(int i = 0; i<udalosti.size(); i++){
            Udalost u = udalosti.get(i);
            /*JSONObject oU = u.returnJSON();
            list.put(oU);*/
        }
        Udalost u = new Udalost();
        u.setId(10000);
        u.setDate("2015-20-12");
        u.setText("zkusebni text");

        list.put(u.returnJSON());

        out.write(list.toString());
        out.flush();
        out.close();

    }

}
