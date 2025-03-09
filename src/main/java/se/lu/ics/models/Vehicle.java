package se.lu.ics.models;

import java.time.LocalDate;

public class Vehicle {
    private String name;
    private VehicleType type;
    private int capacity;
    private String vin;
    private int totalPartsReplaced;
    private LocalDate manufactureDate;

    public Vehicle(String name, VehicleType type, int capacity) {
        this.name = name;
        this.type = type;
        this.capacity = Math.max(0, capacity);
        this.vin = generateVin();
        this.totalPartsReplaced = 0;
        this.manufactureDate = LocalDate.now();
    }

    private String generateVin() {
        return "VEH" + Math.abs(hashCode());
    }

    public int getAge() {
        return LocalDate.now().getYear() - manufactureDate.getYear();
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public VehicleType getType() { return type; }
    public void setType(VehicleType type) { this.type = type; }
    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = Math.max(0, capacity); }
    public String getVin() { return vin; }
    public int getTotalPartsReplaced() { return totalPartsReplaced; }
    public LocalDate getManufactureDate() { return manufactureDate; }
    public void replaceParts(int parts) {
        if (parts > 0) totalPartsReplaced += parts;
    }
    public boolean isDecommissioned() {
        return totalPartsReplaced > 100;
    }
    @Override
    public String toString() {
        return "Vehicle{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", capacity=" + capacity +
                ", vin='" + vin + '\'' +
                ", totalPartsReplaced=" + totalPartsReplaced +
                ", manufactureDate=" + manufactureDate +
                '}';
    }
}