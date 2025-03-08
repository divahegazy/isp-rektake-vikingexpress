package se.lu.ics.models;

import java.time.LocalDate;

/**
 * Represents a record of a service activity performed on a vehicle.
 */
public class ServiceEntry {
    private LocalDate date;
    private String description;
    private double cost;
    private int duration; // e.g., number of hours or days
    private Workshop workshop;
    private Vehicle vehicle;

    // --- Constructor ---
    public ServiceEntry(LocalDate date, String description, double cost, int duration,
                        Workshop workshop, Vehicle vehicle) {
        this.date = date;
        this.description = description;
        this.cost = cost;
        this.duration = duration;
        this.workshop = workshop;
        this.vehicle = vehicle;
    }

    // --- Getters ---
    public LocalDate getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public double getCost() {
        return cost;
    }

    public int getDuration() {
        return duration;
    }

    public Workshop getWorkshop() {
        return workshop;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    // Helper for retrieving VIN or Workshop name if needed
    public String getVehicleVin() {
        return vehicle != null ? vehicle.getVin() : "";
    }

    public String getWorkshopName() {
        return workshop != null ? workshop.getName() : "";
    }

    // --- Setters (added to fix your errors) ---
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setWorkshop(Workshop workshop) {
        this.workshop = workshop;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}