package com.emvrp;

public class Drone {
    private int weight;
    private int batteryCapacity;
    private int maxBattery;

    public Drone(int weight, int batteryCapacity) {
        this.weight = weight;
        this.batteryCapacity = batteryCapacity;
        this.maxBattery = batteryCapacity;
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

    public void decreaseBattery(int energy) {
        this.batteryCapacity -= energy;
    }

    public void increaseBattery(int energy) {
        this.batteryCapacity += energy;
    }

    public void rechargeBattery(int energy) {
        this.batteryCapacity = this.maxBattery;
    }
}
