package com.sebastiangds.flightservice;

import com.sebastiangds.flightservice.backendconnector.EndpointFactory;
import contract.dto.Booking;
import contract.dto.PNRIdentifier;
import contract.dto.User;
import contract.interfaces.BeanInterface;
import org.jboss.ejb.client.RequestSendFailedException;

import javax.ejb.NoSuchEJBException;

public class MainToBeDeleted {
    public static void main(String[] args) {
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
        System.out.println(b);
        System.out.println(b.getPrice());
    }
}
