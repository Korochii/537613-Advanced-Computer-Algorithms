package com.emvrp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        Node depot = new Node(0, 0, 0);
        Node first = new Node(1, 560, 362); //A
        Node second = new Node(2, 504, 469); //B
        Node third = new Node(3, 936, 283); //C
        Edge depotToFirst = new Edge(first.getDistFromDepot(), depot, first);
        Edge depotToSecond = new Edge(second.getDistFromDepot(), depot, second);
        Edge depotToThird = new Edge(third.getDistFromDepot(), depot, third);
        Edge firstToSecond = new Edge(795, first, second);
        Edge secondToFirst = new Edge(795, second, first);
        Edge firstToThird = new Edge(468, first, third);
        Edge thirdToFirst = new Edge(468, third, first);
        Edge secondToThird = new Edge(665, second, third);
        Edge thirdToSecond = new Edge(665, third, second);
        Edge firstToDepot = new Edge(first.getDistFromDepot(), first, depot);
        Edge secondToDepot = new Edge(second.getDistFromDepot(), second, depot);
        Edge thirdToDepot = new Edge(third.getDistFromDepot(), third, depot);

        depot.addNeighbours(depotToFirst);
        depot.addNeighbours(depotToSecond);
        depot.addNeighbours(depotToThird);

        first.addNeighbours(firstToDepot);
        first.addNeighbours(firstToSecond);
        first.addNeighbours(firstToThird);

        second.addNeighbours(secondToDepot);
        second.addNeighbours(secondToFirst);
        second.addNeighbours(secondToThird);

        third.addNeighbours(thirdToDepot);
        third.addNeighbours(thirdToFirst);
        third.addNeighbours(thirdToSecond);

        List<Node> allCustomers = new ArrayList<>();
        allCustomers.add(first);
        allCustomers.add(second);
        allCustomers.add(third);
        EMVRP emvrp = new EMVRP(depot, 2000, 3000, allCustomers);
        int result = emvrp.getBestRoute();
        System.out.println(result);
    }
}
