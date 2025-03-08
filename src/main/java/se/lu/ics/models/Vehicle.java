package se.lu.ics.models;

import java.time.LocalDate;

/**
 * Represents a vehicle in the fleet.
 */
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

    public String getName() {
        return name;
    }

    /** Sets the vehicle's name. */
    public void setName(String name) {
        this.name = name;
    }

    public VehicleType getType() {
        return type;
    }

    /** Sets the vehicle type. */
    public void setType(VehicleType type) {
        this.type = type;
    }

    public int getCapacity() {
        return capacity;
    }

    /** Sets capacity (non-negative). */
    public void setCapacity(int capacity) {
        this.capacity = Math.max(0, capacity);
    }

    public String getVin() {
        return vin;
    }

    public int getTotalPartsReplaced() {
        return totalPartsReplaced;
    }

    public LocalDate getManufactureDate() {
        return manufactureDate;
    }

    /** Adds parts replaced. */
    public void replaceParts(int parts) {
        if (parts > 0) {
            totalPartsReplaced += parts;
        }
    }

    public boolean isDecommissioned() {
        return totalPartsReplaced > 100;
    }

    public int getAge() {
        return (manufactureDate != null) ? LocalDate.now().getYear() - manufactureDate.getYear() : 0;
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