import com.rabbitmq.client.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeoutException;

public class Agency {

    private String name;
    private static Channel channel;
    private static String SYSTEM_EXCHANGE = "cosmicSystemExchange";
    private static String ADMIN_MESSAGES_EXCHANGE = "cosmicSystemAdminExchange";
    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    private void initialize() throws IOException, TimeoutException {
        initializeConnectionAndChannel();
        initializeExchange();
        initializeAgencyName();
        initializeQueues();
    }

    private void initializeConnectionAndChannel() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        channel = connection.createChannel();
    }

    private void initializeExchange() throws IOException {
        channel.exchangeDeclare(SYSTEM_EXCHANGE, BuiltinExchangeType.TOPIC);
        channel.exchangeDeclare(ADMIN_MESSAGES_EXCHANGE, BuiltinExchangeType.TOPIC);
    }

    private void initializeAgencyName() throws IOException {
        System.out.println("Enter name of your agency");
        name = br.readLine();
        System.out.println("Welcome!\nTypes of services:\npassenger transport - P\ncargo transport - C\nputting satellite on orbit - O");
    }

    private void initializeQueues() throws IOException {
        channel.queueDeclare(name, true,true,true, null);
        channel.queueBind(name, SYSTEM_EXCHANGE, "agency." + name);
        channel.queueBind(name, ADMIN_MESSAGES_EXCHANGE, "agencies");
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
        channel.basicConsume(name, false, consumer);
    }

    private void makeCommissions() throws IOException {
        String numberOfCommission;
        TypeOfService type;
        String message;
        while (true) {
            type = askForValidTypeOfService();
            System.out.println("Enter number of commission:");
            numberOfCommission = br.readLine();
            System.out.println("Enter additional info: ");
            message = "New commission:\n" + name + "\n" +  numberOfCommission +"\n" + br.readLine();
            // publish
            channel.basicPublish(SYSTEM_EXCHANGE, type.toString(), null, message.getBytes("UTF-8"));
        }
    }

    private TypeOfService askForValidTypeOfService() throws IOException {
        TypeOfService type = null;
        while(type == null){
            System.out.println("Enter valid type of service (one letter):");
            type = TypeOfService.decodeTypeOfService(br.readLine());
        }
        return type;
    }


    public static void main(String[] argv) throws Exception {
        Agency agency = new Agency();
        // info
        System.out.println("Agency");
        agency.initialize();

        Thread commissionsThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    agency.makeCommissions();
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
                    agency.listen();
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
