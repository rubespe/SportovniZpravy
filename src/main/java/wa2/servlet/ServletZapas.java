package wa2.servlet;



import org.json.JSONArray;
import org.json.JSONObject;
import wa2.Entity.Sport;
import wa2.Entity.Zapas;
import wa2.hibernate.HibernateZapas;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


@WebServlet(name = "ServletZapas")
public class ServletZapas extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json");
// Get the printwriter object from response to write the required json object to the output stream
        PrintWriter out = response.getWriter();
// Assuming your json object is **jsonObject**, perform the following, it will return your json object
        //Logger.getLogger("MyLogger").log(Level.SEVERE, request.getParameter("Category") + " toto prislo");
        int category = 0;
        try {
             category = Integer.valueOf(request.getParameter("Category"));
        }catch(Exception e){
            return;
        }

        List<Zapas> zapasy = HibernateZapas.getAllEventsForCategory(category);

        //System.out.println("pisu event size: " + zapasy.size());
        JSONArray list = new JSONArray();
        for(Zapas e: zapasy){
            JSONObject oC = e.returnJSON();
            list.put(oC);
           // System.out.println("pisu event: " + oC.toString());
        }
        out.write(list.toString());
        out.flush();
        out.close();




    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String eventName = request.getParameter("eventName");
        String eventDate = request.getParameter("eventDate");
        String eventCat = request.getParameter("eventCat");

        //Logger.getLogger("MyLogger").log(Level.SEVERE, request.getParameter("eventCat") + " toto prislo");

        PrintWriter out = response.getWriter();
        List<Sport> cat = new ArrayList<Sport>();
        String [] ids = eventCat.split("\\,");
        if(ids.length == 0 ){
            out.write("Nebyl zadan sport");
            return;
        }

        Zapas e = new Zapas(eventDate,eventName);
        HibernateZapas.saveEvent(e);
        List<Integer> intId = new ArrayList<Integer>();
        for(String s: ids){
            int id = 0;
            try{
                id = Integer.valueOf(s);
                intId.add(id);
            }catch(Exception ex){
                out.write("bad Category " + s);
                return;
            }


        }

        int status = HibernateZapas.saveEventWithC(e, intId);

if(status == -1){
    out.write("bad Category numbers" );
    return;
}


        out.write("Event created");
       // response.setStatus(16);
    out.close();
    }
}
