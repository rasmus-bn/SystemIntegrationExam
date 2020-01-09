package com.sebastiangds.flightservice;

import com.sebastiangds.flightservice.backendconnector.EndpointFactory;
import contract.interfaces.BeanInterface;

public class Main {
    public static void main(String[] args) {
        BeanInterface e = new EndpointFactory().getEndpoint();
        String response = e.whoAmI("SI exam project");
        System.out.println(response);
    }
}
