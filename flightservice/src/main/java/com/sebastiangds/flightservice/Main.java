package com.sebastiangds.flightservice;

import com.sebastiangds.flightservice.backendconnector.EndpointFactory;
import contract.dto.Booking;
import contract.dto.PNRIdentifier;
import contract.dto.User;
import contract.interfaces.BeanInterface;

public class Main {
    public static void main(String[] args) {
        User u = new User(55, 33, "sgd-B2C", "sgd-B2C-pw");
        BeanInterface e = new EndpointFactory().getEndpoint();
        String response = e.whoAmI("SI exam project");
        System.out.println(response);
        Booking b = e.getBooking(u, new PNRIdentifier(1));
        System.out.println(b.getPrice());
    }
}
