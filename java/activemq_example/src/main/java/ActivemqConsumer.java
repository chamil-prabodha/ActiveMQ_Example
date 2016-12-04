import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQBytesMessage;

import javax.jms.*;
import java.io.UnsupportedEncodingException;

/**
 * Created by Chamil Prabodha on 04/12/2016.
 */
public class ActivemqConsumer implements Runnable,ExceptionListener {

    private boolean enabled = true;

    @Override
    public void run() {
        try {

            //Creating a connection factory
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");

            //Creating a connection
            Connection connection = connectionFactory.createConnection();
            connection.start();

            connection.setExceptionListener(this);

            //Creating a session
            Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);

            //Creating a destination (Topic/Queue)
            Destination destination = session.createTopic("ReceiveTopic");

            //Creating a MessageConsumer from the session for topic
            MessageConsumer consumer = session.createConsumer(destination);

            while(enabled){
                //Waiting for a message
                System.out.println("Waiting for a message...");
                Message message = consumer.receive();

                if( message instanceof  TextMessage){
                    TextMessage textMessage = (TextMessage)message;
                    String text = textMessage.getText();
                    System.out.println("Received: "+text);

                }
                else {
                    String msg = new String(((ActiveMQBytesMessage)message).getContent().data,"UTF-8");
                    System.out.println("Received: "+msg);
                }
            }


            consumer.close();
            session.close();
            connection.close();


        } catch (JMSException e) {
            enabled = false;
            System.out.println(e);
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onException(JMSException e) {
        System.out.println("JMS Exception! Client closing...");
    }
}
