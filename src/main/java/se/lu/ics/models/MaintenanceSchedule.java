package se.lu.ics.models;

import java.time.LocalDate;

public class MaintenanceSchedule {
    private LocalDate maintenanceDate;
    private String description;
    private double cost;
    private Workshop workshop;
    private Vehicle vehicle;

    public MaintenanceSchedule(LocalDate maintenanceDate, String description, double cost, Workshop workshop, Vehicle vehicle) {
        if (maintenanceDate == null || description == null || description.isEmpty() || cost < 0 || workshop == null || vehicle == null) {
            throw new IllegalArgumentException("Invalid maintenance schedule parameters.");
        }
        // Additional check: if vehicle is a Large Truck, workshop must be external.
        if (vehicle.getType() == VehicleType.LARGE_TRUCK && workshop.isInternal()) {
            throw new IllegalArgumentException("Large trucks cannot be serviced at internal workshops.");
        }
        this.maintenanceDate = maintenanceDate;
        this.description = description;
        this.cost = cost;
        this.workshop = workshop;
        this.vehicle = vehicle;
    }

    public LocalDate getMaintenanceDate() { return maintenanceDate; }
    public String getDescription() { return description; }
    public double getCost() { return cost; }
    public Workshop getWorkshop() { return workshop; }
    public Vehicle getVehicle() { return vehicle; }
    public void setMaintenanceDate(LocalDate maintenanceDate) { this.maintenanceDate = maintenanceDate; }
    public void setDescription(String description) { this.description = description; }
    public void setCost(double cost) { this.cost = cost; }
    public void setWorkshop(Workshop workshop) { this.workshop = workshop; }
    public void setVehicle(Vehicle vehicle) { this.vehicle = vehicle; }
}