package com.emvrp;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.compare;

public class Node implements Comparable<Node> {
    private final int index;
    private final int weight;
    private List<Edge> neighbours;
    private final int distFromDepot; // Assumes that we have this info for every node at the start

    public Node(int index, int weight, int distFromDepot) {
        this.index = index;
        this.weight = weight;
        this.distFromDepot = distFromDepot;
        this.neighbours = new ArrayList<>();
    }

    public int getIndex() {
        return this.index;
    }

    public int getWeight() {
        return this.weight;
    }

    public int getDistFromDepot() {
        return this.distFromDepot;
    }

    public List<Edge> getNeighbours() {
        return this.neighbours;
    }

    public void setNeighbours(List<Edge> neighbours) {
        this.neighbours = neighbours;
    }

    public void addNeighbours(Edge neighbour) {
        this.neighbours.add(neighbour);
    }

    @Override
    public String toString() {
        return "Node #" + this.index;
    }

    @Override
    public int compareTo(Node node) {
        return compare(this.weight, node.getWeight());
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Node)) {
            return false;
        }
        return this.index == ((Node) obj).getIndex();
    }
}
