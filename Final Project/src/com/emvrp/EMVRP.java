package com.emvrp;

import java.util.BitSet;
import java.util.HashMap;
import java.util.LinkedList;
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
    private List<Node> allCustomers;
    private HashMap<State, Integer> dp;
    private State state;
    private HashMap<BitSet, Integer> weightVector;

    public EMVRP(Node startingNode, int payloadWeight, int droneWeight, List<Node> allCustomers) {
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

        // Handles the rest of the customers
        for (int i = 1; i < Math.pow(2, allCustomers.size()); i++) {
            BitSet currBitSet = convert(i); // E.g. 0001 => visited 1st
            // Select next_customer that has not been visited yet (i.e. bit = 0)
            for (int j = 0; j < allCustomers.size(); j++) {
                if (!currBitSet.get(j)) {
                    // Index of current non-visited node = j
                    // Try to find the source node that has j as dest node
                    Node nextCustomer = allCustomers.get(j);
                    for (Edge edge : nextCustomer.getNeighbours()) {
                        Node prevCustomer = edge.getDest();
                        if (prevCustomer.getIndex() != 0) {
                            BitSet nextBitSet = (BitSet) currBitSet.clone();
                            nextBitSet.set(j);
                            // Update dp 2D array if necessary
                            State nextState = new State(nextBitSet, nextCustomer);
                            State prevState = new State((BitSet) currBitSet.clone(), prevCustomer);
                            int storedCalc = Integer.MAX_VALUE;
                            if (dp.containsKey(prevState)) {
                                int weight = weightVector.get(currBitSet);
                                storedCalc = dp.get(prevState) + energyRequired(weight, edge.getDist());
                            }
                            nextState.addCustomerVisited(prevCustomer);
                            nextState.setCurrentNode(prevCustomer);
                            int currCalc = Integer.MAX_VALUE;
                            if (dp.containsKey(nextState)) {
                                currCalc = dp.get(nextState);
                            }
                            this.dp.put(nextState, Math.min(storedCalc, currCalc));
                            this.weightVector.put(nextBitSet, weightVector.get(currBitSet) - nextCustomer.getWeight());
                        }
                    }
                }
            }
        }
    }

    private int energyRequired(int weight, int distance) {
        return distance * (weight + this.droneWeight);
    }

    private BitSet convert(long value) {
        BitSet bits = new BitSet();
        int index = 0;
        while (value != 0L) {
            if (value % 2L != 0) {
                bits.set(index);
            }
            ++index;
            value = value >>> 1;
        }
        return bits;
    }

}
