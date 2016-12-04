/**
 * Created by Chamil Prabodha on 04/12/2016.
 */
public class ActivemqExample {

    public static void main(String args[]){

        ActivemqProducer producer = new ActivemqProducer();
        ActivemqConsumer consumer = new ActivemqConsumer();

        Thread producerThread = new Thread(producer,"Producer");
        Thread consumerThread = new Thread(consumer,"Consumer 1");

        producerThread.start();
        consumerThread.start();

    }

}
