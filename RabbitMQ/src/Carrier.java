import com.rabbitmq.client.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Carrier {

    public static void main(String[] argv) throws Exception {

        // info
        System.out.println("Carrier");

        // connection & channel
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // exchange
        String EXCHANGE_NAME = "cosmicSystemExchange";
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);


        // queue & bind
        System.out.println("Types of services:\npassenger transport - P\ncargo transport - C\nputting satellite on orbit - O");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter first type of service (one letter): ");
        String firstTypeOfService = br.readLine();
        channel.queueDeclare(firstTypeOfService, true, false, false, null);
        channel.queueBind(firstTypeOfService, EXCHANGE_NAME, firstTypeOfService);
        System.out.println("Enter second type of service (one letter): ");
        String secondTypeOfService = br.readLine();
        channel.queueDeclare(secondTypeOfService, true, false, false, null);
        channel.queueBind(secondTypeOfService, EXCHANGE_NAME, secondTypeOfService);

        channel.basicQos(1);

        // consumer (message handling)
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(message);
                String agencyName = message.substring(0, message.indexOf("\n"));
                String numberOfCommission = message.substring(0, message.indexOf("\n", message.indexOf("\n")+1));
                String returnMessage = "Commission number " + numberOfCommission + " was successfully realized";
                channel.basicAck(envelope.getDeliveryTag(), false);
                channel.basicPublish(EXCHANGE_NAME, agencyName, null, returnMessage.getBytes("UTF-8"));
                System.out.println("Received: " + message);
            }
        };

        // start listening
        System.out.println("Waiting for messages...");
        channel.basicConsume(firstTypeOfService, false, consumer);
        channel.basicConsume(secondTypeOfService, false, consumer);
    }
}
