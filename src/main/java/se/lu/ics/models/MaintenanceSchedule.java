package se.lu.ics.models;

import java.time.LocalDate;

/**
 * Represents a maintenance schedule for a vehicle.
 */
public class MaintenanceSchedule {

    private LocalDate maintenanceDate;
    private String description;
    private double cost;
    private Workshop workshop;
    private Vehicle vehicle;

    public MaintenanceSchedule(LocalDate maintenanceDate, String description,
                               double cost, Workshop workshop, Vehicle vehicle) {
        if (maintenanceDate == null)
            throw new IllegalArgumentException("Maintenance date cannot be null.");
        if (description == null || description.isEmpty())
            throw new IllegalArgumentException("Description cannot be empty.");
        if (cost < 0)
            throw new IllegalArgumentException("Cost cannot be negative.");
        if (workshop == null)
            throw new IllegalArgumentException("Workshop cannot be null.");
        if (vehicle == null)
            throw new IllegalArgumentException("Vehicle cannot be null.");
        this.maintenanceDate = maintenanceDate;
        this.description = description;
        this.cost = cost;
        this.workshop = workshop;
        this.vehicle = vehicle;
    }

    public LocalDate getMaintenanceDate() {
        return maintenanceDate;
    }

    public void setMaintenanceDate(LocalDate maintenanceDate) {
        if (maintenanceDate == null)
            throw new IllegalArgumentException("Maintenance date cannot be null.");
        this.maintenanceDate = maintenanceDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description == null || description.isEmpty())
            throw new IllegalArgumentException("Description cannot be empty.");
        this.description = description;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        if (cost < 0)
            throw new IllegalArgumentException("Cost cannot be negative.");
        this.cost = cost;
    }

    public Workshop getWorkshop() {
        return workshop;
    }

    public void setWorkshop(Workshop workshop) {
        if (workshop == null)
            throw new IllegalArgumentException("Workshop cannot be null.");
        this.workshop = workshop;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        if (vehicle == null)
            throw new IllegalArgumentException("Vehicle cannot be null.");
        this.vehicle = vehicle;
    }

    public boolean isValid() {
        return maintenanceDate != null && description != null && !description.isEmpty()
                && cost >= 0 && workshop != null && vehicle != null;
    }

    @Override
    public String toString() {
        return String.format("MaintenanceSchedule [Date: %s, Description: %s, Cost: %.2f, Workshop: %s, Vehicle: %s]",
                maintenanceDate, description, cost, workshop.getName(), vehicle.getName());
    }
}