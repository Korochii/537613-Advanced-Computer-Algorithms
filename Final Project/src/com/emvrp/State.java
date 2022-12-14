package com.emvrp;

import java.util.BitSet;
import java.util.Objects;

public class State {
    private BitSet customersVisited;
    private Node currentNode;

    public State(BitSet customersVisited, Node currentNode) {
        this.customersVisited = customersVisited;
        this.currentNode = currentNode;
    }

    public BitSet getCustomersVisited() {
        return customersVisited;
    }

    public void setCustomersVisited(BitSet customersVisited) {
        this.customersVisited = customersVisited;
    }

    public Node getCurrentNode() {
        return currentNode;
    }

    public void setCurrentNode(Node currentNode) {
        this.currentNode = currentNode;
    }

    public void addCustomerVisited(Node node) {
        this.customersVisited.set(node.getIndex() - 1);
    }

    @Override
    public String toString() {
        // customersVisited denotes which bit is true
        // E.g. customersVisited={2} -> 2nd bit === 1 -> 3rd customer has been visited (zero indexing)
        return "State{" +
                "customersVisited=" + customersVisited +
                ", currentNode=" + currentNode +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return Objects.equals(customersVisited, state.customersVisited)
                && Objects.equals(currentNode, state.currentNode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customersVisited, currentNode);
    }
}
