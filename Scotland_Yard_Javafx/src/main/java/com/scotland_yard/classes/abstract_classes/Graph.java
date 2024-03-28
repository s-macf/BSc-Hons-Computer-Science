package com.scotland_yard.classes.abstract_classes;

import com.scotland_yard.classes.Location;

public abstract class Graph {
    public abstract Location getNodeByNumber(Integer node_num);

    protected abstract void addNode(Location node_to_add);

    protected abstract void setupNodes();

    protected abstract void setupEdges();

}
