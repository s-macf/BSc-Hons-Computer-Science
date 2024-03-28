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

    private void removeFromAvailableMoves(HashMap<Location, ArrayList<TransportUtilities.TransportType>> currentNeighbours, ArrayList<Location> nodesToRemove){
        nodesToRemove.forEach(currentNeighbours.keySet()::remove);
        nodesToRemove.clear();
    }

    private void removeInvalidNumberOfTicketLocations(HashMap<Location, ArrayList<TransportUtilities.TransportType>> currentNeighbours){
        ArrayList<Location> nodesToRemove = new ArrayList<>();
        for (Location availableMove : currentNeighbours.keySet()) {
            if (this.tickets.get(Ticket.CONCEALED).equals(0) && currentNeighbours.get(availableMove).get(0).equals(TransportUtilities.TransportType.FERRY)) {                 // If out of ferry tickets
                nodesToRemove.add(availableMove);
            }
        }
        removeFromAvailableMoves(currentNeighbours, nodesToRemove);
    }

    @Override
    public HashMap<Location, ArrayList<TransportUtilities.TransportType>> getAvailableMoves(ArrayList<Detective> detectives) {
        HashMap<Location, ArrayList<TransportUtilities.TransportType>> currentNeighbours = this.currentLocation.getNeighboursHashMap();
        ArrayList<Location> nodesToRemove = new ArrayList<>();

        // remove locations with detectives
        for (Location availableMove : currentNeighbours.keySet()) {
            for (Detective detective : detectives) {
                if (detective.getCurrentLocation().equals(availableMove)) {
                    nodesToRemove.add(availableMove);
                }
            }
        }

        removeFromAvailableMoves(currentNeighbours, nodesToRemove);

        // if empty add all locations back (locations with detectives are the only available moves)
        if (currentNeighbours.isEmpty()){
            currentNeighbours = this.currentLocation.getNeighboursHashMap();
        }

        removeInvalidNumberOfTicketLocations(currentNeighbours);

        if (currentNeighbours.isEmpty()){
            currentNeighbours = this.currentLocation.getNeighboursHashMap();
            removeInvalidNumberOfTicketLocations(currentNeighbours);
        }

        return currentNeighbours;
    }

    @Override
    protected void setupStartingTickets() {                                 // Assigns starting tickets for Mr X
        this.tickets.put(Ticket.DOUBLE, DEFAULT_DOUBLE_TICKETS);
        this.tickets.put(Ticket.CONCEALED, DEFAULT_BLACK_TICKETS);
    }

}
