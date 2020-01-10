package com.sebastiangds.flightservice.grpc;

import com.sebastiangds.flightservice.backendconnector.EndpointFactory;
import com.sebastiangds.flightservice.lib.*;
import contract.dto.*;
import contract.interfaces.BeanInterface;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;

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
            Flight f = t.getFlight();
            TicketInfo tInfo = TicketInfo.newBuilder()
                    .setFlightId(f.getId())
                    .setFirstName(t.getPassenger().getFirstName())
                    .setLastName(t.getPassenger().getLastName())
                    .setDepAiportName(f.getDepAirport().getName())
                    .setDepAiportIata(f.getDepAirport().getIata())
                    .setArrAiportName(f.getArrAirport().getName())
                    .setArrAiportIata(f.getArrAirport().getIata())
                    .build();
            bBuilder.addTickets(tInfo);
        }


        /*int ticketIndex = 0;
        for (Ticket t : booking.getTickets()) {
            Flight f = t.getFlight();
            TicketInfo tInfo = TicketInfo.newBuilder()
                    .setFlightId(f.getId())
                    .setFirstName(t.getPassenger().getFirstName())
                    .setLastName(t.getPassenger().getLastName())
                    .setDepAiportName(f.getDepAirport().getName())
                    .setDepAiportIata(f.getDepAirport().getIata())
                    .setArrAiportName(f.getDepAirport().getName())
                    .setArrAiportIata(f.getDepAirport().getIata())
                    .build();
            bBuilder.setTickets(ticketIndex, tInfo);
            ticketIndex++;
        }*/

        BookingInfo bookingInfo = bBuilder.build();
        GetBookingReply reply = GetBookingReply.newBuilder().setBooking(bookingInfo).build();
        System.out.println(booking.getPrice());
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
