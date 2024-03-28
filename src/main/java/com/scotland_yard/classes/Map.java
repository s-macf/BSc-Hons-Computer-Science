package com.scotland_yard.classes;

import com.scotland_yard.classes.TransportUtilities.TransportType;
import com.scotland_yard.classes.Utilities.FileReader;
import com.scotland_yard.classes.abstract_classes.Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Map extends Graph {

    private static final String NODES_FILEPATH = "/Scotland_Yard_Graph/vertices.txt";
    private static final String EDGES_FILEPATH = "/Scotland_Yard_Graph/edges.txt";
    private static final String RANDOMS_FILEPATH = "/Scotland_Yard_Graph/random_starts.txt";
    private final HashMap<Integer, Location> board_map = new HashMap<>();   // hashset of all locations key = number, value = node object
    private final HashSet<Location> randomStarts = new HashSet<>();

    public Map() {
        setupNodes();               // Sets up graph vertices
        setupEdges();               // Sets up graph edges
        readRandomStarts();
    }

    public HashSet<Location> getRandomStarts() {     // returns random starts set
        return this.randomStarts;
    }

    @Override
    public Location getNodeByNumber(Integer node_num) {
        return board_map.get(node_num);                     // returns the object node with requested number
    }

    @Override
    protected void addNode(Location node_to_add) {
        this.board_map.put(node_to_add.getNumber(), node_to_add);   // Adds new location to hashmap
    }

    @Override
    protected void setupNodes() {
        ArrayList<String> nodesData = FileReader.readData(NODES_FILEPATH);  // Reads node data from file
        for (String line : nodesData) {
            String[] current_line = line.split("\t");                                 // separates location number from transport method
            Integer location_num = Integer.parseInt(current_line[0]);
            TransportType type = TransportUtilities.getTransportType(current_line[1]);
            Location new_node = new Location(location_num, type);                                 // Creates a new location node
            addNode(new_node);                                                              // Adds node to hashmap
        }
    }

    @Override
    protected void setupEdges() {
        ArrayList<String> edgesData = FileReader.readData(EDGES_FILEPATH);  // Reads edge data from file
        for (String line : edgesData) {
            String[] current_line = line.split("\t");                                 // split data into node1 : node2 : transport
            Location node1 = board_map.get(Integer.parseInt(current_line[0]));              // get node 1 object
            Location node2 = board_map.get(Integer.parseInt(current_line[1]));              // get node 2 object
            TransportType transportMethod = TransportUtilities.getTransportType(current_line[2]);   // get transport enum

            node1.addNeighbour(node2, transportMethod);     // Add neighbour to node 1
            //node2.addNeighbour(node1, transportMethod);     // Add neighbour to node 2
        }
    }

    private void readRandomStarts() {         // Reads random starting locations from file
        ArrayList<String> edgesData = FileReader.readData(RANDOMS_FILEPATH);
        for (String line : edgesData) {
            Integer randomStart = Integer.parseInt(line);
            this.randomStarts.add(this.getNodeByNumber(randomStart));
        }
    }

}
