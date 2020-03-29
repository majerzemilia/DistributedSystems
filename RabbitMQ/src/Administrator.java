import com.rabbitmq.client.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeoutException;

public class Administrator {

    private static Channel channel;
    private static String EXCHANGE_NAME = "cosmicSystemExchange";
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    private static void initialize() throws IOException, TimeoutException {
        initializeConnectionAndChannel();
        initializeExchange();
        initializeAdminQueue();
    }

    private static void initializeConnectionAndChannel() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        channel = connection.createChannel();
    }

    private static void initializeExchange() throws IOException {
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
    }

    private static void initializeAdminQueue() throws IOException {
        channel.queueDeclare("admin", true,true,true, null); //queue for admin mode
        channel.queueBind("admin", EXCHANGE_NAME, "#");
    }

    private static void listen() throws IOException {
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                channel.basicAck(envelope.getDeliveryTag(), false);
                System.out.println("Received: " + message.substring(1));
            }
        };
        channel.basicConsume("admin", false, consumer);
    }

    private static void sendMessage() throws IOException {
        System.out.println("Welcome! Types of receivers:\nA - all agencies\nC - all carriers\nALL - all");
        String message;
        while (true) {
            String type = askForValidReceiversType();
            //System.out.println(type);
            System.out.println("Enter message:");
            message = "B" + br.readLine();
            // publish
            publishMessage(message, type);
        }
    }

    private static String askForValidReceiversType() throws IOException {
        String type = null;
        while(type == null){
            System.out.println("Enter valid receivers type:");
            type = decodeReceiversType(br.readLine());
        }
        return type;
    }

    private static String decodeReceiversType(String code){
        switch(code){
            case "A":
                return "agencies";
            case "C":
                return "carriers";
            case "ALL":
                return "all";
            default:
                return null;
        }
    }

    private static void publishMessage(String message, String type) throws IOException {
        if(type.equals("all")) {
            channel.basicPublish(EXCHANGE_NAME, "agencies", null, message.getBytes("UTF-8"));
            channel.basicPublish(EXCHANGE_NAME, "carriers", null, message.getBytes("UTF-8"));
        }
        else channel.basicPublish(EXCHANGE_NAME, type, null, message.getBytes("UTF-8"));
    }

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        // info
        System.out.println("Administrator");
        initialize();

        Thread listenThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    listen();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread.sleep(300);
        Thread senderThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sendMessage();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        listenThread.start();
        senderThread.start();
        listenThread.join();
        senderThread.join();
    }
}
