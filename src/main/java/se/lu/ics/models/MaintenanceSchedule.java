package se.lu.ics.models;

import java.time.LocalDate;

public class MaintenanceSchedule {
    private LocalDate maintenanceDate;
    private String description;
    private double cost;
    private Workshop workshop;
    private Vehicle vehicle;

    public MaintenanceSchedule(LocalDate maintenanceDate, String description, double cost, Workshop workshop, Vehicle vehicle) {
        this.maintenanceDate = maintenanceDate;
        this.description = description;
        this.cost = cost;
        this.workshop = workshop;
        this.vehicle = vehicle;
    }

    public LocalDate getMaintenanceDate() { return maintenanceDate; }
    public void setMaintenanceDate(LocalDate date) { this.maintenanceDate = date; }

    public String getDescription() { return description; }
    public void setDescription(String d) { this.description = d; }

    public double getCost() { return cost; }
    public void setCost(double c) { this.cost = c; }

    public Workshop getWorkshop() { return workshop; }
    public void setWorkshop(Workshop w) { this.workshop = w; }

    public Vehicle getVehicle() { return vehicle; }
    public void setVehicle(Vehicle v) { this.vehicle = v; }
}