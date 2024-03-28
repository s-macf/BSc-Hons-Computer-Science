package com.scotland_yard.classes.abstract_classes;

import com.scotland_yard.classes.Detective;
import com.scotland_yard.classes.Location;
import com.scotland_yard.classes.TransportUtilities.TransportType;
import com.scotland_yard.classes.TransportUtilities.Ticket;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class Player {

    private final Color playerColour;
    protected HashMap<Ticket, Integer> tickets = new HashMap<>();
    protected Location currentLocation;

    public Player(Color colour, Location startingLocation) {  // Receives player colour and random starting location
        this.playerColour = colour;
        this.currentLocation = startingLocation;
    }

    public Location getCurrentLocation() {      // returns current location of player
        return this.currentLocation;
    }

    public abstract HashMap<Location, ArrayList<TransportType>> getAvailableMoves(ArrayList<Detective> detectives); // Returns all neighbours for current location

    public Color getPlayerColour() {       // For UI Implementation of the board
        return this.playerColour;
    }

    public void setLocation(Location new_location) { // Updates player's location
        this.currentLocation = new_location;
    }

    public String toString() {                    // returns colour as string for detectives
        String result;
        if (this.playerColour.equals(Color.BLACK)) {
            result = "Black";
        } else if (this.playerColour.equals(Color.BLUE)) {
            result = "Blue";
        } else if (this.playerColour.equals(Color.RED)) {
            result = "Red";
        } else if (this.playerColour.equals(Color.YELLOW)) {
            result = "Yellow";
        } else if (this.playerColour.equals(Color.GREEN)) {
            result = "Green";
        } else {
            result = "Mr X";                // if player is Mr X returns Mr X instead of colour
        }

        return result;
    }

    public void removeTicket(Ticket ticketType) {
        this.tickets.put(ticketType, this.tickets.get(ticketType) - 1);
    }

    public Integer getRemainingTickets(Ticket ticketType) {         // returns the number tickets remaining for specific ticket type
        return this.tickets.get(ticketType);
    }

    protected abstract void setupStartingTickets();

}
