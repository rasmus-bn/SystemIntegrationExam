package com.sebastiangds.flightservice.components;

import com.google.common.hash.Hashing;
import contract.dto.Flight;
import contract.dto.Passenger;
import contract.dto.Ticket;

import java.nio.charset.StandardCharsets;

public class TicketHelper {
    public String generateTicketId(Ticket ticket) {
        Passenger p = ticket.getPassenger();
        String tInfoString = "" +
                ticket.getFlight().getId() +
                p.getFirstName() +
                p.getLastName() +
                p.getDob().getTime();
        return Hashing.sha256()
                .hashString(tInfoString, StandardCharsets.UTF_8)
                .toString();
    }
}
