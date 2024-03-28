package com.scotland_yard.classes.interfaces;

import com.scotland_yard.classes.Location;
import com.scotland_yard.classes.TransportUtilities;

import java.util.ArrayList;
import java.util.HashSet;

public interface Node {

    TransportUtilities.TransportType getTransportType();
    void addNeighbour(Location neighbour_node, TransportUtilities.TransportType transportType);

    HashSet<Location> getNeighbours();

    Integer getNumber();

    ArrayList<TransportUtilities.TransportType> getNeighbourTransportMethods(Location neighbourNode);

}
