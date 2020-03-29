import com.rabbitmq.client.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeoutException;

public class Agency {

    private static String name;
    private static Channel channel;
    private static String EXCHANGE_NAME = "cosmicSystemExchange";
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    private static void initialize() throws IOException, TimeoutException {
        // connection & channel
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        channel = connection.createChannel();

        // exchange
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

        System.out.println("Enter name of your agency");
        name = br.readLine();
        System.out.println("Welcome!\nTypes of services:\npassenger transport - P\ncargo transport - C\nputting satellite on orbit - O");

        //queue for confirmations
        channel.queueDeclare(name, true,true,true, null);
        channel.queueBind(name, EXCHANGE_NAME, name);
    }

    private static void listenForConfirmation() throws IOException {
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                channel.basicAck(envelope.getDeliveryTag(), false);
                System.out.println("Received: " + message);
            }
        };
        channel.basicConsume(name, false, consumer);
    }

    private static void makeCommissions() throws IOException {

        String numberOfCommission;
        while (true) {
            // read msg
            System.out.println("Enter type of commission (one letter):");
            String key = br.readLine();
            System.out.println("Enter number of commission:");
            numberOfCommission = br.readLine();
            System.out.println("Enter additional info: ");
            String message = name + "\n" +  numberOfCommission +"\n" + br.readLine();
            // break condition
            if ("exit".equals(message)) {
                break;
            }
            // publish
            channel.basicPublish(EXCHANGE_NAME, key, null, message.getBytes("UTF-8"));
            System.out.println("Sent: " + message);
        }
    }


    public static void main(String[] argv) throws Exception {

        // info
        System.out.println("Agency");
        initialize();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    makeCommissions();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread.sleep(300);
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    listenForConfirmation();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }

}
