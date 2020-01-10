package com.sebastiangds.flightservice.grpc;

import com.sebastiangds.flightservice.backendconnector.EndpointFactory;
import com.sebastiangds.flightservice.lib.FlightServiceGrpc;
import com.sebastiangds.flightservice.lib.GetBookingReply;
import com.sebastiangds.flightservice.lib.GetBookingRequest;
import contract.dto.Booking;
import contract.dto.PNRIdentifier;
import contract.dto.User;
import contract.interfaces.BeanInterface;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;

@GRpcService
public class FlightServiceImpl extends FlightServiceGrpc.FlightServiceImplBase {

    private final User user = new User(55, 33, "sgd-B2C", "sgd-B2C-pw");

    @Override
    public void getBooking(GetBookingRequest request, StreamObserver<GetBookingReply> responseObserver) {
        PNRIdentifier pnr = new PNRIdentifier(request.getBookingId());

        BeanInterface backend = new EndpointFactory().getEndpoint();

        Booking booking = backend.getBooking(user, pnr);

        System.out.println(pnr.getPnr());
        System.out.println(booking.getPrice());

        GetBookingReply reply = GetBookingReply.newBuilder()
                .setBookingId(booking.getPnr().getPnr())
                .setPrice(booking.getPrice()).build();

        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
