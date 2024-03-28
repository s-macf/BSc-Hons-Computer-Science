package com.scotland_yard.AI;

import com.scotland_yard.GameEngine;
import com.scotland_yard.classes.Detective;
import com.scotland_yard.classes.Location;
import com.scotland_yard.classes.Mr_X;
import com.scotland_yard.classes.TransportUtilities;
import com.scotland_yard.classes.TransportUtilities.Ticket;
import com.scotland_yard.classes.TransportUtilities.TransportType;
import com.scotland_yard.classes.Utilities.RandomGenerator;
import javafx.util.Pair;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Detective_AI {

    public static Pair<Location, Ticket> getMove(Detective detective, HashMap<Location, ArrayList<TransportType>> availableMovesHashMap, int currentRound, Mr_X mr_x) {
        ArrayList<Location> availableMoves = new ArrayList<>(availableMovesHashMap.keySet());
        if (availableMoves.size() == 0) {
            return new Pair<>(null, null);
        }

        int randInt = RandomGenerator.getInt(availableMoves.size());
        Location newLocation = availableMoves.get(randInt);
        ArrayList<TransportType> transportOptions = detective.getCurrentLocation().getNeighbourTransportMethods(newLocation);

        randInt = RandomGenerator.getInt(transportOptions.size());
        Ticket usedTicket = TransportUtilities.transportTypeToTicket(transportOptions.get(randInt));

        return new Pair<>(newLocation, usedTicket);
    }
}
