package wa2.servlet;



import org.json.JSONArray;
import org.json.JSONObject;
import wa2.Entity.Sport;
import wa2.hibernate.HibernateSport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "ServletSport")
public class ServletSport extends HttpServlet {

   // CustVehRepository custVehRep = new CustVehRepository();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //System.out.println("pisu kategorii:");
        response.setContentType("application/json");
// Get the printwriter object from response to write the required json object to the output stream
        PrintWriter out = response.getWriter();
// Assuming your json object is **jsonObject**, perform the following, it will return your json object
/*
        if(request.isUserInRole("admin")) {
            response.addHeader("showInput", "yes");
        }else{
            response.addHeader("showInput", "no");
        }*/
        response.addHeader("showInput", "yes");
        response.flushBuffer();

        List<Sport> sporty = HibernateSport.getAllCategories();
        JSONArray list = new JSONArray();
        for(Sport c: sporty){
            JSONObject oC = c.returnJSON();
            list.put(oC);
        }
        out.write(list.toString());
        out.flush();
        out.close();


    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("nameC");

        Sport c = new Sport(name);

        PrintWriter out = response.getWriter();
        try{
            HibernateSport.saveCategory(c);

        }catch(Exception e){

            out.write("error");
            out.close();
            return;
        }

        out.write("done");
        out.close();
    }
}
