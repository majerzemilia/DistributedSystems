import com.rabbitmq.client.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeoutException;

public class Carrier {

    private String name;
    private static Channel channel;
    private static String SYSTEM_EXCHANGE = "cosmicSystemExchange";
    private static String ADMIN_MESSAGES_EXCHANGE = "cosmicSystemAdminExchange";
    private TypeOfService firstTypeOfService;
    private TypeOfService secondTypeOfService;
    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    private void initialize() throws IOException, TimeoutException {
        initializeConnectionAndChannel();
        initializeExchange();
        initializeCarrierName();
        initializeTypesOfService();
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

    private void initializeCarrierName() throws IOException {
        System.out.println("Enter name of your company");
        name = br.readLine();
        System.out.println("Welcome!\nTypes of services:\npassenger transport - P\ncargo transport - C\nputting satellite on orbit - O");
    }

    private void initializeTypesOfService() throws IOException {
        while (firstTypeOfService == null) {
            System.out.println("Enter valid type of first service (one letter): ");
            firstTypeOfService = TypeOfService.decodeTypeOfService(br.readLine());
        }
        while (secondTypeOfService == null) {
            System.out.println("Enter valid type of second service (one letter): ");
            secondTypeOfService = TypeOfService.decodeTypeOfService(br.readLine());
        }
    }


    private void initializeQueues() throws IOException {
        channel.queueDeclare(firstTypeOfService.toString(), true, false, false, null);
        channel.queueBind(firstTypeOfService.toString(), SYSTEM_EXCHANGE, firstTypeOfService.toString());
        channel.queueDeclare(secondTypeOfService.toString(), true, false, false, null);
        channel.queueBind(secondTypeOfService.toString(), SYSTEM_EXCHANGE, secondTypeOfService.toString());
        channel.basicQos(1);
        channel.queueDeclare(name, true, true, true, null); //queue for admin mode
        channel.queueBind(name, ADMIN_MESSAGES_EXCHANGE, "carriers");
    }

    private void listenForCommissions() throws IOException {
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                if(message.length() >= 14 && message.substring(0, 14).equals("New commission")) handleAgencyMessage(message, envelope);
                else handleAdminMessage(message, envelope);
            }
        };
        System.out.println("Waiting for commissions...");
        channel.basicConsume(firstTypeOfService.toString(), false, consumer);
        channel.basicConsume(secondTypeOfService.toString(), false, consumer);
        channel.basicConsume(name, false, consumer);
    }

    private void handleAgencyMessage(String message, Envelope envelope) throws IOException {
        int lastBlockIndex = message.indexOf("\n");
        int currentBlockIndex = message.indexOf("\n", lastBlockIndex+1);
        String agencyName = message.substring(lastBlockIndex + 1, currentBlockIndex);
        lastBlockIndex = currentBlockIndex;
        currentBlockIndex = message.indexOf("\n", lastBlockIndex+1);
        String numberOfCommission = message.substring(lastBlockIndex + 1, currentBlockIndex);
        String returnMessage = "Realized commission:\n" + "Commission number " + numberOfCommission + " was successfully realized by company " + name;
        channel.basicAck(envelope.getDeliveryTag(), false);
        channel.basicPublish(SYSTEM_EXCHANGE, "agency."+ agencyName, null, returnMessage.getBytes("UTF-8"));
        System.out.println("Realized commission from agency: " + agencyName + " number: " + numberOfCommission + "\n");
    }

    private void handleAdminMessage(String message, Envelope envelope) throws IOException {
        System.out.println(message + "\n");
        channel.basicAck(envelope.getDeliveryTag(), false);
    }

    public static void main(String[] argv) throws Exception {
        Carrier carrier = new Carrier();
        // info
        System.out.println("Carrier");

        carrier.initialize();
        carrier.listenForCommissions();

    }
}
