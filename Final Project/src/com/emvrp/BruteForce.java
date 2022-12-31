package com.emvrp;

import java.util.BitSet;
import java.util.HashMap;
import java.util.List;

public class BruteForce {
    private Node startingNode; // By default, it should be the depot
    private int payloadWeight; // Total weight of payload
    private int droneWeight;
    private List<Node> allCustomers;
    private Drone drone;
    public BruteForce(Node startingNode, int payloadWeight, List<Node> allCustomers, Drone drone) {
        this.startingNode = startingNode;
        this.payloadWeight = payloadWeight;
        this.allCustomers = allCustomers;
        this.drone = drone;
        this.droneWeight = drone.getWeight();

    }

    public int getBestRoute() {
        int numOfCustomers = allCustomers.size();
        int[] permutation = new int[numOfCustomers];
        for (int i = 0; i < numOfCustomers; i++) {
            permutation[i] = i;
        }

        int bestTourCost = Integer.MAX_VALUE;

        do {
            int currCost = computeCost(permutation);

            if (currCost < bestTourCost) {
                bestTourCost = currCost;
            }
        } while (nextPermutation(permutation));

        return bestTourCost;
    }

    public int computeCost(int[] permutation) {
        int cost = 0;
        int numOfCustomers = allCustomers.size();
        int currentPayload = this.payloadWeight;
        int currentBatteryLevel = this.drone.getBatteryCapacity();
        int maxBatteryCapacity = this.drone.getBatteryCapacity();
        boolean isValidPermutation = true;

        // Depot to first node
        Node firstCustomer = allCustomers.get(permutation[0]);
        int initCost  = energyRequired(currentPayload, firstCustomer.getDistFromDepot());
        currentPayload -= firstCustomer.getWeight();
        currentBatteryLevel -= initCost;
        cost += initCost;

        // All other nodes
        for (int i = 0; i < numOfCustomers - 1; i++) {
            Node currCustomer = allCustomers.get(permutation[i]);
            Node nextCustomer = allCustomers.get(permutation[i + 1]);
            boolean isNeighbour = false;
            Edge connection = null;

            // Skips the remaining logic if the 2 nodes are not connected
            for (Edge edge : currCustomer.getNeighbours()) {
                if (edge.getDest().equals(nextCustomer)) {
                    isNeighbour = true;
                    connection = edge;
                    break;
                }
            }

            if (isNeighbour) {
                // Check if there is a need to return to depot to recharge
                int currCost = energyRequired(currentPayload, connection.getDist());
                int costToDepot = energyRequired(currentPayload - nextCustomer.getWeight(),
                        nextCustomer.getDistFromDepot());
                int totalEnergyRequired = currCost + costToDepot;
                if (totalEnergyRequired > currentBatteryLevel) {
                    cost += energyRequired(currentPayload, currCustomer.getDistFromDepot());
                    cost += energyRequired(currentPayload, nextCustomer.getDistFromDepot());
                    currentBatteryLevel = maxBatteryCapacity
                            - energyRequired(currentPayload, nextCustomer.getDistFromDepot()); // Recharge at depot
                } else {
                    currentBatteryLevel -= currCost;
                    cost += currCost;
                }
                currentPayload -= nextCustomer.getWeight();

            } else {
                isValidPermutation = false;
            }
        }

        // Last Node to Depot
        cost += energyRequired(0, allCustomers.get(permutation[numOfCustomers - 1]).getDistFromDepot());

        if (isValidPermutation) {
            return cost;
        } else {
            return Integer.MAX_VALUE;
        }
    }

    public static boolean nextPermutation(int[] sequence) {
        int first = getFirst(sequence);
        if (first == -1) return false;
        int toSwap = sequence.length - 1;
        while (sequence[first] >= sequence[toSwap]) --toSwap;
        swap(sequence, first++, toSwap);
        toSwap = sequence.length - 1;
        while (first < toSwap) swap(sequence, first++, toSwap--);
        return true;
    }

    private static int getFirst(int[] sequence) {
        for (int i = sequence.length - 2; i >= 0; --i) if (sequence[i] < sequence[i + 1]) return i;
        return -1;
    }

    private static void swap(int[] sequence, int i, int j) {
        int tmp = sequence[i];
        sequence[i] = sequence[j];
        sequence[j] = tmp;
    }

    private int energyRequired(int weight, int distance) {
        return distance * (weight + this.droneWeight);
    }

}
