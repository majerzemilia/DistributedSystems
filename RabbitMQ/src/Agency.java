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
        initializeConnectionAndChannel();
        initializeExchange();
        initializeAgencyName();
        initializeQueueForConfirmations();
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

    private static void initializeAgencyName() throws IOException {
        System.out.println("Enter name of your agency");
        name = br.readLine();
        System.out.println("Welcome!\nTypes of services:\npassenger transport - P\ncargo transport - C\nputting satellite on orbit - O");
    }

    private static void initializeQueueForConfirmations() throws IOException {
        channel.queueDeclare(name, true,true,true, null);
        channel.queueBind(name, EXCHANGE_NAME, "agency." + name);
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
        TypeOfService type;
        String message;
        while (true) {
            type = askForValidTypeOfService();
            System.out.println("Enter number of commission:");
            numberOfCommission = br.readLine();
            System.out.println("Enter additional info: ");
            message = name + "\n" +  numberOfCommission +"\n" + br.readLine();
            // publish
            channel.basicPublish(EXCHANGE_NAME, type.toString(), null, message.getBytes("UTF-8"));
        }
    }

    private static TypeOfService askForValidTypeOfService() throws IOException {
        TypeOfService type = null;
        while(type == null){
            System.out.println("Enter valid type of service (one letter):");
            type = TypeOfService.decodeTypeOfService(br.readLine());
        }
        return type;
    }


    public static void main(String[] argv) throws Exception {

        // info
        System.out.println("Agency");
        initialize();

        Thread commissionsThread = new Thread(new Runnable() {
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
        Thread confirmationsThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    listenForConfirmation();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        commissionsThread.start();
        confirmationsThread.start();
        commissionsThread.join();
        confirmationsThread.join();
    }

}
