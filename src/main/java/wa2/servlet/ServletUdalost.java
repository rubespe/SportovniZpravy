package wa2.servlet;



import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import org.json.JSONArray;
import org.json.JSONObject;
import wa2.Entity.Zapas;
import wa2.Entity.Udalost;
import wa2.hibernate.HibernateZapas;
import wa2.hibernate.HibernateUdalost;
import wa2.websocket.WebSocket;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "ServletUdalost")
public class ServletUdalost extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json");
// Get the printwriter object from response to write the required json object to the output stream
        PrintWriter out = response.getWriter();
        String event = request.getParameter("Event");
        String msgLastId = request.getParameter("LastId");
        int msgLastIdint;
        int eventId = 0;
        try{
            eventId = Integer.valueOf(event);
            msgLastIdint = Integer.valueOf(msgLastId);
        }catch (Exception e){
            return;
        }
// Assuming your json object is **jsonObject**, perform the following, it will return your json object
    /*
        if(request.isUserInRole("admin")) {
            response.addHeader("showInput", "yes");
        }else{
            response.addHeader("showInput", "no");
        }*/
        response.addHeader("showInput", "yes");

        response.flushBuffer();

        List<Udalost> messages = new ArrayList<Udalost>();

        if(msgLastIdint == -1) {//get all cat

           messages = HibernateUdalost.getAllMessagesForEvent(eventId);
        }else{

            messages.add(HibernateUdalost.getMessageBiggerId(msgLastIdint, eventId));

        }

        JSONArray list = new JSONArray();
        for(Udalost m: messages){

            JSONObject oC = m.returnJSON();
            list.put(oC);

        }
        out.write(list.toString());
        out.flush();
        out.close();

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String text = request.getParameter("Text");
        String event = request.getParameter("Event");
        int eventId = 0;
        try{
            eventId = Integer.valueOf(event);
        }catch (Exception e){
            return;
        }


        Zapas z = null;

        PrintWriter out = response.getWriter();
        try{
           z =  HibernateZapas.getEventById(eventId);

        }catch(Exception ex){

            out.write("error - bad event id");
            out.close();
            return;
        }

        Udalost m = new Udalost(new Date().toString(), text,z);
        System.out.println(m.toString());
        try{
            HibernateUdalost.saveMessage(m);

        }catch(Exception ex){

            out.write("error - message was not saved" + ex.toString());
            out.close();
            return;
        }

        out.write("done");
        out.close();
        Logger.getLogger("MyLogger").log(Level.SEVERE, eventId + " pridana udalost a ted ji poslat");
        send(z.getId());
    }


    private final static String QUEUE_NAME = "messageQ";
    private static final String EXCHANGE_NAME = "topic_logs";


    public static void send(int zapas){
        try {
            ConnectionFactory factory = new ConnectionFactory();
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            factory.setHost("localhost");

            connection = factory.newConnection();
            channel = connection.createChannel();

            channel.exchangeDeclare(EXCHANGE_NAME, "topic");

            String routingKey = String.valueOf(zapas);
            //String routingKey = "1";
            String message =  String.valueOf(zapas);

            Logger.getLogger("MyLogger").log(Level.SEVERE, "routingKey " + routingKey + " message " + message);

            channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + routingKey + "':'" + message + "'");
        }catch(Exception e){
            System.out.println(e.toString());

        }
    }

}
