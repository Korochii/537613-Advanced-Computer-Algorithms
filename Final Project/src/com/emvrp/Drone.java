package com.emvrp;

public class Drone {
    private int weight;
    private int batteryCapacity;

    public Drone(int weight, int batteryCapacity) {
        this.weight = weight;
        this.batteryCapacity = batteryCapacity;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(int batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }
}
