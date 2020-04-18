import com.rabbitmq.client.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeoutException;

public class Administrator {

    private static Channel channel;
    private static String SYSTEM_EXCHANGE = "cosmicSystemExchange";
    private static String ADMIN_MESSAGES_EXCHANGE = "cosmicSystemAdminExchange";
    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    private void initialize() throws IOException, TimeoutException {
        initializeConnectionAndChannel();
        initializeExchange();
        initializeAdminQueue();
    }

    private void initializeConnectionAndChannel() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        channel = connection.createChannel();
    }

    private void initializeExchange() throws IOException {
        channel.exchangeDeclare(SYSTEM_EXCHANGE, BuiltinExchangeType.TOPIC);
        channel.exchangeDeclare(ADMIN_MESSAGES_EXCHANGE, BuiltinExchangeType.DIRECT);
    }

    private void initializeAdminQueue() throws IOException {
        channel.queueDeclare("admin", true,true,true, null); //queue for admin mode
        channel.queueBind("admin", SYSTEM_EXCHANGE, "#");
    }

    private void listen() throws IOException {
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                channel.basicAck(envelope.getDeliveryTag(), false);
                System.out.println(message + "\n");
            }
        };
        channel.basicConsume("admin", false, consumer);
    }

    private void sendMessage() throws IOException {
        System.out.println("Welcome! Types of receivers:\nA - all agencies\nC - all carriers\nALL - all");
        String message;
        while (true) {
            String type = askForValidReceiversType();
            System.out.println("Enter message:");
            message = "Admin message:\n" + br.readLine();
            // publish
            publishMessage(message, type);
        }
    }

    private String askForValidReceiversType() throws IOException {
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
            channel.basicPublish(ADMIN_MESSAGES_EXCHANGE, "agencies", null, message.getBytes("UTF-8"));
            channel.basicPublish(ADMIN_MESSAGES_EXCHANGE, "carriers", null, message.getBytes("UTF-8"));
        }
        else channel.basicPublish(ADMIN_MESSAGES_EXCHANGE, type, null, message.getBytes("UTF-8"));
    }

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Administrator administrator = new Administrator();
        // info
        System.out.println("Administrator");
        administrator.initialize();

        Thread listenThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    administrator.listen();
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
                    administrator.sendMessage();
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
