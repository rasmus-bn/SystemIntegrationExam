package com.sebastiangds.flightservice.grpc;

import com.google.common.hash.Hashing;
import com.sebastiangds.flightservice.backendconnector.EndpointFactory;
import com.sebastiangds.flightservice.components.TicketHelper;
import com.sebastiangds.flightservice.lib.*;
import contract.dto.*;
import contract.interfaces.BeanInterface;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;

import java.nio.charset.StandardCharsets;

@GRpcService
public class FlightServiceImpl extends FlightServiceGrpc.FlightServiceImplBase {

    private final User user = new User(55, 33, "sgd-B2C", "sgd-B2C-pw");

    @Override
    public void getBooking(GetBookingRequest request, StreamObserver<GetBookingReply> responseObserver) {
        PNRIdentifier pnr = new PNRIdentifier(request.getBookingId());
        System.out.println(pnr.getPnr());
        BeanInterface backend = new EndpointFactory().getEndpoint();

        Booking booking = backend.getBooking(user, pnr);

        System.out.println(booking);
        System.out.println(pnr.getPnr());
        System.out.println(booking.getPrice());

        BookingInfo.Builder bBuilder = BookingInfo.newBuilder();

        bBuilder.setBookingId(booking.getPnr().getPnr());
        bBuilder.setPrice(booking.getPrice());

        System.out.println("tickets");
        System.out.println(booking.getTickets().size());

        for (Ticket t : booking.getTickets()) {
            Passenger p = t.getPassenger();
            Flight f = t.getFlight();
            String tId = new TicketHelper().generateTicketId(t);

            TicketInfo tInfo = TicketInfo.newBuilder()
                    .setTicketId(tId)
                    .setFlightId(f.getId())
                    .setFirstName(p.getFirstName())
                    .setLastName(p.getLastName())
                    .setDepAiportName(f.getDepAirport().getName())
                    .setDepAiportIata(f.getDepAirport().getIata())
                    .setArrAiportName(f.getArrAirport().getName())
                    .setArrAiportIata(f.getArrAirport().getIata())
                    .build();
            bBuilder.addTickets(tInfo);
        }

        BookingInfo bookingInfo = bBuilder.build();
        GetBookingReply reply = GetBookingReply.newBuilder().setBooking(bookingInfo).build();
        System.out.println(booking.getPrice());
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
