package com.emvrp;

import java.util.HashSet;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        Node depot = new Node(0, 0, 0);
        Node first = new Node(1, 560, 362);
        Node second = new Node(2, 504, 469);
        Node third = new Node(3, 936, 283);
        Edge depotToFirst = new Edge(first.getDistFromDepot(), depot, first);
        Edge depotToSecond = new Edge(second.getDistFromDepot(), depot, second);
        Edge depotToThird = new Edge(third.getDistFromDepot(), depot, third);

        depot.addNeighbours(depotToFirst);
        depot.addNeighbours(depotToSecond);
        depot.addNeighbours(depotToThird);

        Set<Node> allCustomers = new HashSet<>();
        allCustomers.add(first);
        allCustomers.add(second);
        allCustomers.add(third);
        EMVRP emvrp = new EMVRP(depot, 2000, 3000, allCustomers);
        emvrp.getBestRoute();
    }
}
