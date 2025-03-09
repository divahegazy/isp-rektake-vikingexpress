package se.lu.ics.models;

import java.time.LocalDate;

public class ServiceEntry {
    private LocalDate date;
    private String description;
    private double cost;
    private int duration;
    private Workshop workshop;
    private Vehicle vehicle;

    public ServiceEntry(LocalDate date, String description, double cost, int duration, Workshop workshop, Vehicle vehicle) {
        this.date = date;
        this.description = description;
        this.cost = cost;
        this.duration = duration;
        this.workshop = workshop;
        this.vehicle = vehicle;
    }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate d) { this.date = d; }

    public String getDescription() { return description; }
    public void setDescription(String desc) { this.description = desc; }

    public double getCost() { return cost; }
    public void setCost(double c) { this.cost = c; }

    public int getDuration() { return duration; }
    public void setDuration(int d) { this.duration = d; }

    public Workshop getWorkshop() { return workshop; }
    public void setWorkshop(Workshop w) { this.workshop = w; }

    public Vehicle getVehicle() { return vehicle; }
    public void setVehicle(Vehicle v) { this.vehicle = v; }

    public String getVehicleVin() {
        return vehicle != null ? vehicle.getVin() : "";
    }

    public String getWorkshopName() {
        return workshop != null ? workshop.getName() : "";
    }
}