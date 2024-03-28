package com.scotland_yard.classes;

import com.scotland_yard.classes.TransportUtilities.TransportType;
import com.scotland_yard.classes.interfaces.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Location implements Node {

    private final Integer location_number;
    private final HashMap<Location, ArrayList<TransportType>> neighbours = new HashMap<>();
    private final TransportType transportType;

    public Location(Integer nodeNum, TransportType type) {
        this.location_number = nodeNum;
        this.transportType = type;
    }

    @Override
    public TransportType getTransportType(){
        return this.transportType;
    }

    @Override
    public void addNeighbour(Location neighbour_node, TransportType transport_method) {
        this.neighbours.putIfAbsent(neighbour_node, new ArrayList<>());   // Add neighbour and transport method to lookup table if key absent
        this.neighbours.get(neighbour_node).add(transport_method);        // add to transport_method to transport methods array
    }

    @Override
    public HashSet<Location> getNeighbours() {
        return new HashSet<>(this.neighbours.keySet());                 // return all neighbours
    }

    public HashMap<Location, ArrayList<TransportType>> getNeighboursHashMap() {
        HashMap<Location, ArrayList<TransportType>> copyiedHashMap = new HashMap<>();
        for (Map.Entry<Location, ArrayList<TransportType>> entry : this.neighbours.entrySet()) {
            copyiedHashMap.put(entry.getKey(), new ArrayList<>(entry.getValue()));
        }
        return copyiedHashMap;
    }

    @Override
    public Integer getNumber() {
        return this.location_number;            // return number of the location
    }

    @Override
    public ArrayList<TransportType> getNeighbourTransportMethods(Location neighbourNode) {
        return this.neighbours.get(neighbourNode);                        // return transport method to neighbour node (taxi, bus, underground, ferry)
    }
}