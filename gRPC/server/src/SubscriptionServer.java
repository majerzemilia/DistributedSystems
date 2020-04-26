import io.grpc.Server;
import io.grpc.ServerBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sr.grpc.gen.Event;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import static java.lang.Thread.sleep;

public class SubscriptionServer {
    private static final Logger logger = Logger.getLogger(SubscriptionServer.class.getName());

    private int port = 50051;
    private Server server;
    private Generator generator = new Generator();

    private ObservableList<Event> events = FXCollections.observableArrayList(new ArrayList<>());

    private void start() throws IOException {
        server = ServerBuilder.forPort(port)
                .addService(new SubscriptionImpl(events))
                .build()
                .start();
        logger.info("Server started, listening on " + port);
        events.add(generator.generateEvent());
        events.add(generator.generateEvent());
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                System.err.println("* shutting down gRPC server since JVM is shutting down");
                SubscriptionServer.this.stop();
                System.err.println("* server shut down");
            }
        });

        populateMeetings();
    }

    private void populateMeetings(){
        new Thread(() -> {
            while(true) {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                events.add(generator.generateEvent());
                events.remove(0);
            }
        }).start();
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        final SubscriptionServer server = new SubscriptionServer();
        server.start();
        server.blockUntilShutdown();
    }
}
