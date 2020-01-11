package com.sebastiangds.flightservice.grpc;

import com.google.common.hash.Hashing;
import com.sebastiangds.flightservice.backendconnector.EndpointFactory;
import com.sebastiangds.flightservice.components.TicketHelper;
import com.sebastiangds.flightservice.lib.*;
import contract.dto.*;
import contract.interfaces.BeanInterface;
import io.grpc.stub.StreamObserver;
import logging.Sender;
import org.lognet.springboot.grpc.GRpcService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;

@GRpcService
public class FlightServiceImpl extends FlightServiceGrpc.FlightServiceImplBase {

    private final User user = new User(55, 33, "sgd-B2C", "sgd-B2C-pw");

    @Override
    public void getBooking(GetBookingRequest request, StreamObserver<GetBookingReply> responseObserver) {
        BeanInterface backend = null;
        Sender send = null;
        Booking booking = null;
        PNRIdentifier pnr = new PNRIdentifier(request.getBookingId());
        try {
            backend = new EndpointFactory().getEndpoint();
            send = new Sender();
            booking = backend.getBooking(user, pnr);

            if (booking == null) {
                send.makeLog("FlightServiceImpl", Level.WARNING, "A user search on a non existing booking  with pnr:", "" + request.getBookingId());
            }
        } catch (IOException e) {
            send.makeLog("FlightServiceImpl", Level.SEVERE, "Can not find the user, or the booking id", e.getMessage());
            e.printStackTrace();
        } catch (TimeoutException e) {
            send.makeLog("FlightServiceImpl", Level.SEVERE, "Timeout Exception", e.getMessage());
            e.printStackTrace();
        }
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
        send.makeLog("FlightSericeImpl", Level.FINE, "A user checked this booking with Pnr:", "" + booking.getPnr().getPnr());
    }
}
