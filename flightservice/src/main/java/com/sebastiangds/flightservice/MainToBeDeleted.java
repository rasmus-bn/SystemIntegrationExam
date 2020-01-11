package com.sebastiangds.flightservice;

import com.google.protobuf.Message;
import com.sebastiangds.flightservice.backendconnector.EndpointFactory;
import com.sebastiangds.flightservice.components.TicketHelper;
import contract.dto.Booking;
import contract.dto.PNRIdentifier;
import contract.dto.Ticket;
import contract.dto.User;
import contract.interfaces.BeanInterface;


import logging.Sender;
import org.jboss.ejb.client.RequestSendFailedException;

import javax.ejb.NoSuchEJBException;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;

public class MainToBeDeleted {
    public static void main(String[] args) throws IOException, TimeoutException {
        User u = new User(55, 33, "sgd-B2C", "sgd-B2C-pw");
        BeanInterface beanI = new EndpointFactory().getEndpoint();
        System.out.println("init******************");
        String response = "";
        try {
            response = beanI.whoAmI("SI exam project");
        } catch (RequestSendFailedException e) {
            System.out.println("*****************Main******************'" + e.getMessage());
            System.out.println(e.getCause());
            System.out.println(e.getLocalizedMessage());
            System.out.println(e.getStackTrace().toString());
            System.out.println(e.getClass());

        }
        System.out.println(response);
        Booking b = beanI.getBooking(u, new PNRIdentifier(1));
        Ticket t = (Ticket) b.getTickets().toArray()[0];
        System.out.println(new TicketHelper().generateTicketId(t));
        System.out.println(b);
        System.out.println(b.getPrice());

        Sender sender = new Sender();
        sender.makeLog("MaintoBeDeleted", Level.WARNING,"hello from flightserivce","its me mo");
    }
}
