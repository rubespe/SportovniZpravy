package wa2.websocket;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.util.HashSet;

public class Worker implements Runnable {

    volatile HashSet<String> BindingKeys;
    volatile boolean run = true;

    public Worker() {
        BindingKeys = new HashSet<String>();
    }

    public void addBindingKey(String key){
        BindingKeys.add(key);
    }

    public void deleteBindingKey(String key){
        BindingKeys.remove(key);
    }

    public void run() {
        run = true;
        //String QUEUE_NAME = "messageQ";
        String EXCHANGE_NAME = "topic_logs";
        while (run)
        {

            try
            {
                Thread.sleep(1000);
                System.out.println("waiting");
            }
            catch (InterruptedException ie)
            {
                System.err.println("Interrupted");
                return;
            }
            try{
                ConnectionFactory factory = new ConnectionFactory();
                factory.setHost("localhost");
                Connection connection = factory.newConnection();
                Channel channel = connection.createChannel();

                channel.exchangeDeclare(EXCHANGE_NAME, "topic");
                String queueName = channel.queueDeclare().getQueue();


                for(String key : BindingKeys) {
                    channel.queueBind(queueName, EXCHANGE_NAME, key);
                }

                System.out.println(" [*] Waiting for messages.");

                QueueingConsumer consumer = new QueueingConsumer(channel);
                channel.basicConsume(queueName, true, consumer);

                //timeout 1s
                QueueingConsumer.Delivery delivery = consumer.nextDelivery(1000);

                if(delivery != null) {
                    String message = new String(delivery.getBody(), "UTF-8");
                    String routingKey = delivery.getEnvelope().getRoutingKey();

                    System.out.println(" [x] Received '" + routingKey + "':'" + message + "'");
                    int addedId = Integer.valueOf(message);
                    WebSocket.sendMessage(addedId, message);
                }

            }catch(Exception e){
                System.out.println(e.toString());

            }
        }
    }

    public void cancel(){
        run = false;
    }

    public boolean getState(){
        return run;
    }
}
