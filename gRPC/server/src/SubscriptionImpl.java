import io.grpc.stub.ServerCallStreamObserver;
import io.grpc.stub.StreamObserver;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import sr.grpc.gen.*;

public class SubscriptionImpl extends SubscriptionGrpc.SubscriptionImplBase {

    private ObservableList<Event> events;

    public SubscriptionImpl(ObservableList<Event> events) {
        this.events = events;
    }

    @Override
    public void informAboutMeeting(SubscriptionRequest request, StreamObserver<Event> responseObserver) {
        System.out.println("Inform about meeting");
        ServerCallStreamObserver streamObserver = (ServerCallStreamObserver) responseObserver;
        ListChangeListener<Event> listener = new ListChangeListener<Event>() {
            @Override
            public void onChanged(Change<? extends Event> c) {
                while(c.next()) {
                    if (c.wasAdded()) {
                        for (Event event : c.getAddedSubList()) {
                            if (event.getCategory().equals(request.getCategory())) {
                                responseObserver.onNext(event);
                                break;
                            }
                        }
                    }
                }
            }
        };
        events.addListener(listener);
        while(true){
            if(streamObserver.isCancelled()){
                events.removeListener(listener);
                return;
            }
        }
    }

    @Override
    public void ping(PingMsg msg, StreamObserver<PingMsg> responseObserver){
        System.out.println("Ping request");
        responseObserver.onNext(msg);
        responseObserver.onCompleted();
    }
}
