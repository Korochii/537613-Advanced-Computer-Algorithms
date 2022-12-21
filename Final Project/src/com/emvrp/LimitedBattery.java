package com.emvrp;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;

/**
 * Enhanced EMVRP with limited battery capacity
 *
 * @author Terng Yan Long
 */
public class LimitedBattery {
    private Node startingNode; // By default, it should be the depot
    private int payloadWeight; // Total weight of payload
    private int droneWeight;
    private List<Node> allCustomers;
    private HashMap<State, Integer> dp;
    private State state;
    private HashMap<BitSet, Integer> weightVector;
    private HashMap<State, List<State>> route;
    private Drone drone;

    public LimitedBattery(Node startingNode, int payloadWeight, List<Node> allCustomers, Drone drone) {
        this.startingNode = startingNode;
        this.payloadWeight = payloadWeight;
        this.droneWeight = drone.getWeight();
        this.allCustomers = allCustomers;
        this.dp = new HashMap<>();
        int bitsRequired = Integer.SIZE - Integer.numberOfLeadingZeros(allCustomers.size());
        this.state = new State(new BitSet(bitsRequired), startingNode); // i.e. E({0}, 0)
        this.weightVector = new HashMap<>();
        this.route = new HashMap<>();
        this.drone = drone;
    }

    public int getBestRoute() {
        // Handles the first customer
        for (Edge next_customer : startingNode.getNeighbours()) {
            Node dest = next_customer.getDest();
            State tempState = new State((BitSet) state.getCustomersVisited().clone(), state.getCurrentNode());
            tempState.addCustomerVisited(dest);
            tempState.setCurrentNode(dest);
            int currEnergyRequired = energyRequired(payloadWeight, dest.getDistFromDepot());
            this.dp.put(tempState, currEnergyRequired);
            this.weightVector.put(tempState.getCustomersVisited(), payloadWeight - dest.getWeight());
            List<State> list = new ArrayList<>();
            list.add(tempState);
            route.put(tempState, list);
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
                        if (prevCustomer.getIndex() != 0 && currBitSet.get(prevCustomer.getIndex() - 1)) {
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

                            int currCalc = Integer.MAX_VALUE;
                            if (dp.containsKey(nextState)) {
                                currCalc = dp.get(nextState);
                            }

                            int currEnergyRequired = Math.min(storedCalc, currCalc);
                            this.dp.put(nextState, currEnergyRequired);
                            this.weightVector.put(nextBitSet, weightVector.get(currBitSet) - nextCustomer.getWeight());

                            List<State> list;
                            if (route.containsKey(nextState)) {
                                list = route.get(nextState);
                            } else {
                                list = new ArrayList<>();
                            }
                            list.add(prevState);
                            route.put(nextState, list);
                        }
                    }
                }
            }
        }

        // Return from last node to depot
        int minCost = Integer.MAX_VALUE;
        State finalState = null;
        for (Node customer: allCustomers) {
            long temp = (long) (Math.pow(2, allCustomers.size()) - 1);
            State currState = new State(convert(temp), customer);
            int costForCurrState = dp.get(currState) + energyRequired(0, customer.getDistFromDepot());
            if (costForCurrState < minCost) {
                minCost = costForCurrState;
                finalState = currState;
            }
        }
        return minCost + calculateAdditionalEnergy(finalState);
    }

    private int calculateAdditionalEnergy(State finalState) {
        int numOfNodes = allCustomers.size();
        int maxBatteryCapacity = this.drone.getBatteryCapacity();
        int currentBatteryLevel = maxBatteryCapacity; // Drone start with 100% battery
        int extraEnergyRequired = 0; // Result of this function
        int currentPayloadWeight = this.payloadWeight;

        // Trace path taken for optimal route, and add it into an array for further processing
        Node[] pathTaken = new Node[numOfNodes];
        for (int i = numOfNodes - 1; i >= 0; i--) {
            pathTaken[i] = finalState.getCurrentNode();
            List<State> states = route.get(finalState);
            int count = Integer.MAX_VALUE;
            for (State state : states) {
                if (dp.get(state) < count) {
                    count = dp.get(state);
                    finalState = state;
                }
            }
        }

        // Calculate remaining battery level/payload weight after visiting the first node
        currentBatteryLevel -= energyRequired(currentPayloadWeight, pathTaken[0].getDistFromDepot());
        currentPayloadWeight -= pathTaken[0].getWeight();

        // Check for each node outside of depot, excluding the last node
        for (int j = 0; j < numOfNodes - 1; j++) {
            Node currNode = pathTaken[j];
            Node nextNode = pathTaken[j+1];
            int energyFromCurrToNext = 0;
            for (Edge edge : currNode.getNeighbours()) {
                if (edge.getDest().equals(nextNode)) {
                    energyFromCurrToNext = energyRequired(currentPayloadWeight, edge.getDist());
                }
            }

            // Unload at next node
            currentPayloadWeight -= nextNode.getWeight();
            int energyFromNextToDepot = energyRequired(currentPayloadWeight, nextNode.getDistFromDepot());
            int totalEnergyRequired = energyFromCurrToNext + energyFromNextToDepot;

            if (totalEnergyRequired > currentBatteryLevel) {
                int temp = energyRequired(currentPayloadWeight, currNode.getDistFromDepot());
                extraEnergyRequired += (2 * temp);
                currentBatteryLevel = maxBatteryCapacity - temp; // Recharge at depot
            }
            currentBatteryLevel -= energyFromCurrToNext;
        }
        return extraEnergyRequired;

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
