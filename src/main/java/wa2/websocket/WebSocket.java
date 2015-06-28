package wa2.websocket;

import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

@ServerEndpoint("/online")
public class WebSocket {

    private static HashMap<Integer,Set<Session>> clients = new HashMap<Integer,Set<Session>>();
    private static Worker messageListener;
    private static Thread t;
    private static AtomicBoolean initialized = new AtomicBoolean(false);

    public static void sendMessage(int zapas,String message)
            throws IOException {

        synchronized(clients){
            for(Session s : clients.get(zapas)){
                s.getBasicRemote().sendText(message);
            }
        }
    }

    @OnOpen
    public void onOpen (Session session) throws InterruptedException {

        Logger.getLogger("MyLogger").log(Level.SEVERE, "websocket open");

        int zapasId = getZapasId(session);

        initListener();

        messageListener.addBindingKey(String.valueOf(zapasId));

        if (!clients.containsKey(zapasId)) {
            clients.put(zapasId, Collections.synchronizedSet(new HashSet<Session>()));
        }

        Set<Session> sessions = clients.get(zapasId);

        sessions.add(session);

        //If thread was terminated
        if(!messageListener.getState()){
            t = new Thread(messageListener);
            t.start();
        }
    }

    private void initListener() {
        if (initialized.compareAndSet(false, true)) {
            messageListener = new Worker();
            t = new Thread(messageListener);
            t.start();
        }
    }

    @OnClose
    public void onClose (Session session) throws InterruptedException {
        // Remove session from the connected sessions set
        Logger.getLogger("MyLogger").log(Level.SEVERE, "zaviram websocket");
        int zapasId = getZapasId(session);

        Set<Session> sessions = clients.get(zapasId);
        sessions.remove(session);

        //if there is no client watching the match
        //delete binding key for topic
        //delete match id
        if(sessions.size()==0) {
            messageListener.deleteBindingKey(String.valueOf(zapasId));
            clients.remove(zapasId);
        }

        //if there is no client watching any match, stop listening to message topics
        //stop thread
        if(clients.size()==0){
            messageListener.cancel();
            t.join();
        }

    }

    private int getZapasId(Session session){
        String q = session.getRequestURI().getQuery();

        String[] parameters = q.split("\\=");

        int zapas = 0;
        try{
            zapas = Integer.parseInt(parameters[1]);
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }

        return zapas;
    }
}
