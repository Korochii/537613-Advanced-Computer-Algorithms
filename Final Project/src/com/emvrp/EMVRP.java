package com.emvrp;

import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Classic EMVRP without enhancements.
 *
 * @author Terng Yan Long
 */
public class EMVRP {
    private List<Integer> route; // Stores the resulting route
    private Node startingNode; // By default, it should be the depot
    private int payloadWeight; // Total weight of payload
    private int droneWeight;
    private Set<Node> allCustomers;
    private HashMap<State, Integer> dp;
    private State state;
    private HashMap<BitSet, Integer> weightVector;

    public EMVRP(Node startingNode, int payloadWeight, int droneWeight, Set<Node> allCustomers) {
        this.startingNode = startingNode;
        this.payloadWeight = payloadWeight;
        this.droneWeight = droneWeight;
        this.allCustomers = allCustomers;
        this.dp = new HashMap<>();
        int bitsRequired = Integer.SIZE - Integer.numberOfLeadingZeros(allCustomers.size());
        this.state = new State(new BitSet(bitsRequired), startingNode); // i.e. E({0}, 0)
        this.weightVector = new HashMap<>();
    }

    public void getBestRoute() {
        // Handles the first customer
        for (Edge next_customer : startingNode.getNeighbours()) {
            Node dest = next_customer.getDest();
            State tempState = new State((BitSet) state.getCustomersVisited().clone(), state.getCurrentNode());
            tempState.addCustomerVisited(dest);
            tempState.setCurrentNode(dest);
            this.dp.put(tempState, energyRequired(payloadWeight, dest.getDistFromDepot()));
            this.weightVector.put(tempState.getCustomersVisited(), payloadWeight + droneWeight - dest.getWeight());
        }

        

    }

    private int energyRequired(int weight, int distance) {
        return distance * (weight + this.droneWeight);
    }

}
