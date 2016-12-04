import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by Chamil Prabodha on 04/12/2016.
 */
public class ActivemqProducer implements Runnable {

    private boolean enabled = true;

    @Override
    public void run() {
        try{
            //Creating connection factory
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");

            //Creating a connection
            Connection connection = connectionFactory.createConnection();
            connection.start();

            //Creating a session
            Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);

            //Creating a destination (Topic/Queue)
            Destination destination = session.createTopic("ExampleTopic");

            //Creating a  MessageProducer from session for topic
            MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            int count = 0;
            while(enabled){
                count++;
                //Creating a message
                StringBuffer text = new StringBuffer("id: "+count+" : Example Message. From: ");
                text.append(Thread.currentThread().getName()+" : ");
                text.append(Thread.currentThread().getId());

                TextMessage message = session.createTextMessage(text.toString());

                //Send the message
//                System.out.println(" : Message sent: "+message.getText()+" : "+ Thread.currentThread().getName());
                producer.send(message);

                Thread.sleep(1000);


            }
            session.close();
            connection.close();
        }

       catch (JMSException e) {
           enabled = false;
           System.out.println(e);
           e.printStackTrace();
       }

        catch (Exception e){
            enabled = false;
            e.printStackTrace();
        }
    }
}
