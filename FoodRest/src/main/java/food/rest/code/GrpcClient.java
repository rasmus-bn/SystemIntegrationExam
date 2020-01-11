package food.rest.code;


import food.rest.code.logic.FlightServiceGrpc;
import food.rest.code.logic.GetBookingReply;
import food.rest.code.logic.GetBookingRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GrpcClient {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 6565)
                .usePlaintext()
                .build();

        FlightServiceGrpc.FlightServiceBlockingStub stub
                = FlightServiceGrpc.newBlockingStub(channel);

        GetBookingReply getBooking = stub.getBooking(GetBookingRequest.newBuilder()
                .setBookingId(1)
                .build());

        System.out.println("Response received from server:\n" + getBooking);

        channel.shutdown();
    }
}
