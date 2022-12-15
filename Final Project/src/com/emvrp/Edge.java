package com.emvrp;

import static java.lang.Integer.compare;

public class Edge implements Comparable<Edge> {
    private int dist;
    private Node source;
    private Node dest;

    public Edge(int dist, Node source, Node dest) {
        this.dist = dist;
        this.source = source;
        this.dest = dest;
    }

    public void setDist(int dist) {
        this.dist = dist;
    }

    public int getDist() {
        return this.dist;
    }

    public void setSource(Node source) {
        this.source = source;
    }

    public Node getSource() {
        return this.source;
    }

    public void setDest(Node dest) {
        this.dest = dest;
    }

    public Node getDest() {
        return this.dest;
    }

    @Override
    public int compareTo(Edge edge) {
        return compare(this.dist, edge.getDist());
    }
}
