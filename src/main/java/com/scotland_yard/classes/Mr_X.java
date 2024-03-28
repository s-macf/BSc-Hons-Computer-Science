package com.scotland_yard.classes;

import com.scotland_yard.classes.TransportUtilities.Ticket;
import com.scotland_yard.classes.abstract_classes.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Mr_X extends Player {
    private static final int DEFAULT_BLACK_TICKETS = 5;         // Default starting ticket values
    private static final int DEFAULT_DOUBLE_TICKETS = 2;                // 1 black ticket for number of detectives
    private static final Color colour = Color.WHITE;

    public Mr_X(Color colour, Location startingLocation) {
        super(colour, startingLocation);
        setupStartingTickets();
    }

    public static Color getColour() {
        return colour;
    }

    @Override
    public HashMap<Location, ArrayList<TransportUtilities.TransportType>> getAvailableMoves(ArrayList<Detective> detectives) {
        HashMap<Location, ArrayList<TransportUtilities.TransportType>> currentNeighbours = this.currentLocation.getNeighboursHashMap();
        ArrayList<Location> nodesToRemove = new ArrayList<>();
        for (Location availableMove : currentNeighbours.keySet()) {
            if (this.tickets.get(Ticket.CONCEALED).equals(0)) {                 // If out of ferry tickets
                currentNeighbours.get(availableMove).remove(TransportUtilities.TransportType.FERRY);
            }

            if (currentNeighbours.get(availableMove).size() == 0) {             // If available transport is empty
                nodesToRemove.add(availableMove);
            }
        }

        for (Location node : nodesToRemove) {        // Remove all nodes to be removed
            currentNeighbours.keySet().remove(node);
        }

        return currentNeighbours;
    }

    @Override
    protected void setupStartingTickets() {                                 // Assigns starting tickets for Mr X
        this.tickets.put(Ticket.DOUBLE, DEFAULT_DOUBLE_TICKETS);
        this.tickets.put(Ticket.CONCEALED, DEFAULT_BLACK_TICKETS);
    }

}
