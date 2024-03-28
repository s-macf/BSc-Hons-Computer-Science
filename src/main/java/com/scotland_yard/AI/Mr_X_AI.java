package com.scotland_yard.AI;

import com.scotland_yard.classes.Location;
import com.scotland_yard.classes.Mr_X;
import com.scotland_yard.classes.TransportUtilities;
import com.scotland_yard.classes.TransportUtilities.Ticket;
import com.scotland_yard.classes.TransportUtilities.TransportType;
import com.scotland_yard.classes.Utilities.RandomGenerator;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

public class Mr_X_AI {
    public static Pair<Location, Ticket> getMove(Mr_X mr_x, HashMap<Location, ArrayList<TransportType>> availableMovesHashMap) {
        ArrayList<Location> availableMoves = new ArrayList<>(availableMovesHashMap.keySet());
        int randomChoice = RandomGenerator.getInt(availableMoves.size());
        Location newLocation = availableMoves.get(randomChoice);

        ArrayList<TransportUtilities.TransportType> transportOptions = mr_x.getCurrentLocation().getNeighbourTransportMethods(newLocation);

        int randInt = RandomGenerator.getInt(transportOptions.size());
        Ticket usedTicket = TransportUtilities.transportTypeToTicket(transportOptions.get(randInt));

        return new Pair<>(newLocation, usedTicket);
    }
}