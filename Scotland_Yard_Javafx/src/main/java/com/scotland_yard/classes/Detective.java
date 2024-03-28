package com.scotland_yard.classes;

import com.scotland_yard.AI.Detective_AI;
import com.scotland_yard.classes.TransportUtilities.Ticket;
import com.scotland_yard.classes.TransportUtilities.TransportType;
import com.scotland_yard.classes.abstract_classes.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Detective extends Player {
    private static final int DEFAULT_TAXI_TICKETS = 10;         // Default starting ticket values
    private static final int DEFAULT_BUS_TICKETS = 8;
    private static final int DEFAULT_UNDERGROUND_TICKETS = 4;
    private static final Color[] availableColours = {Color.BLUE, Color.BLACK, Color.red, Color.GREEN, Color.YELLOW};

    public Detective(Color colour, Location startingLocation) {
        super(colour, startingLocation);
        setupStartingTickets();
    }

    public Detective(Color colour, Location currentLocation, HashMap<Ticket, Integer> tickets){
        super(colour, currentLocation, tickets);
    }

    public static Color[] getAvailableColours() {
        return availableColours;
    }

    @Override
    protected void setupStartingTickets() {                 // Assigns starting tickets to the detective
        this.tickets.put(Ticket.TAXI, DEFAULT_TAXI_TICKETS);
        this.tickets.put(Ticket.BUS, DEFAULT_BUS_TICKETS);
        this.tickets.put(Ticket.UNDERGROUND, DEFAULT_UNDERGROUND_TICKETS);
    }

    @Override
    public HashMap<Location, ArrayList<TransportType>> getAvailableMoves(ArrayList<Detective> detectives) {
        // Returns available for current location and remaining tickets

        HashMap<Location, ArrayList<TransportType>> currentNeighbours = this.currentLocation.getNeighboursHashMap();
        ArrayList<Location> nodesToRemove = new ArrayList<>();
        for (Location availableMove : currentNeighbours.keySet()) {
            // If ferry remove
            if (currentLocation.getNeighbourTransportMethods(availableMove).get(0).equals(TransportType.FERRY)) {
                nodesToRemove.add(availableMove);
            }
            // If out of taxi tickets
            if (this.tickets.get(Ticket.TAXI) <= 0) {
                currentNeighbours.get(availableMove).remove(TransportType.TAXI);
            }
            // If out of bus tickets
            if (this.tickets.get(Ticket.BUS) <= 0) {
                currentNeighbours.get(availableMove).remove(TransportType.BUS);
            }
            // If out of underground tickets
            if (this.tickets.get(Ticket.UNDERGROUND) <= 0) {
                currentNeighbours.get(availableMove).remove(TransportType.UNDERGROUND);
            }
            // If available transport is empty
            if (currentNeighbours.get(availableMove).size() == 0) {
                nodesToRemove.add(availableMove);
            }
            // Check if location has a detective already
            for (Detective detective : detectives) {
                if (detective.currentLocation.equals(availableMove)) {
                    nodesToRemove.add(availableMove);
                }
            }
        }

        // Remove all nodes to be removed
        for (Location node : nodesToRemove) {
            currentNeighbours.keySet().remove(node);
        }

        return currentNeighbours;
    }

    public HashMap<Ticket, Integer> copyTickets(){
        return new HashMap<>(this.tickets);
    }

}