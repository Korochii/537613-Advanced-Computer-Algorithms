package com.emvrp;

import java.util.BitSet;
import java.util.Objects;

public class State {
    private BitSet customersVisited;
    private Node currentNode;
    // private Node prevNode;

    public State(BitSet customersVisited, Node currentNode) {
        this.customersVisited = customersVisited;
        this.currentNode = currentNode;
        // this.prevNode = null;
    }

//    public State(BitSet customersVisited, Node currentNode, Node prevNode) {
//        this.customersVisited = customersVisited;
//        this.currentNode = currentNode;
//        // this.prevNode = prevNode;
//    }

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

//    public void setPrevNode(Node node) {
//        this.prevNode = node;
//    }
    @Override
    public String toString() {
//        StringBuilder s = new StringBuilder();
//        for(int i = 0; i < customersVisited.length();  i++) {
//            s.insert(0, customersVisited.get(i) ? 1: 0);
//        }
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
