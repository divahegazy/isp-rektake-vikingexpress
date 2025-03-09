package se.lu.ics.models;

import java.util.*;

public class FleetManager {
    private List<Vehicle> vehicles = new ArrayList<>();
    private List<Workshop> workshops = new ArrayList<>();
    private List<ServiceEntry> serviceEntries = new ArrayList<>();
    private List<MaintenanceSchedule> maintenanceSchedules = new ArrayList<>();

    // --- Add Vehicle (ensures uniqueness by VIN) ---
    public boolean addVehicle(Vehicle vehicle) {
        if (isVehicleExist(vehicle.getVin())) {
            System.out.println("[ERROR] Duplicate VIN is not allowed: " + vehicle.getVin());
            return false;
        }
        vehicles.add(vehicle);
        return true;
    }

    public Vehicle getVehicleByVin(String vin) {
        return vehicles.stream().filter(v -> v.getVin().equals(vin)).findFirst().orElse(null);
    }

    public boolean removeVehicle(String vin) {
        return vehicles.removeIf(v -> v.getVin().equals(vin));
    }

    public List<Vehicle> getAllVehicles() {
        return vehicles;
    }

    // --- Workshop Management ---
    public boolean addWorkshop(Workshop workshop) {
        if (isWorkshopExist(workshop.getName())) {
            System.out.println("[ERROR] Duplicate workshop: " + workshop.getName());
            return false;
        }
        workshops.add(workshop);
        return true;
    }

    public Workshop getWorkshopByName(String name) {
        return workshops.stream().filter(w -> w.getName().equals(name)).findFirst().orElse(null);
    }

    public boolean removeWorkshop(String name) {
        return workshops.removeIf(w -> w.getName().equals(name));
    }

    public List<Workshop> getAllWorkshops() {
        return workshops;
    }

    // --- Service Entry Management ---
    public boolean addServiceEntry(ServiceEntry entry) {
        if (entry.getCost() < 0) {
            System.out.println("[ERROR] Negative cost is not allowed.");
            return false;
        }
        serviceEntries.add(entry);
        return true;
    }

    public boolean removeServiceEntry(ServiceEntry entry) {
        return serviceEntries.remove(entry);
    }

    public List<ServiceEntry> getAllServiceEntries() {
        return serviceEntries;
    }

    // --- Maintenance Schedule Management ---
    public boolean addMaintenanceSchedule(MaintenanceSchedule schedule) {
        maintenanceSchedules.add(schedule);
        return true;
    }

    public boolean removeMaintenanceSchedule(MaintenanceSchedule schedule) {
        return maintenanceSchedules.remove(schedule);
    }

    public List<MaintenanceSchedule> getAllMaintenanceSchedules() {
        return maintenanceSchedules;
    }

    // --- Cost Calculations ---
    public double getTotalServiceCost(String vin) {
        return serviceEntries.stream()
                .filter(e -> e.getVehicle().getVin().equals(vin))
                .mapToDouble(ServiceEntry::getCost)
                .sum();
    }

    public double getAverageServiceCost() {
        return serviceEntries.stream()
                .mapToDouble(ServiceEntry::getCost)
                .average().orElse(0.0);
    }

    public boolean checkServiceCostWarning(String vin) {
        return getTotalServiceCost(vin) > 10000;
    }

    public boolean checkDecommissionWarning(String vin) {
        Vehicle v = getVehicleByVin(vin);
        return v != null && v.isDecommissioned();
    }

    // --- Helper Methods ---
    private boolean isVehicleExist(String vin) {
        return vehicles.stream().anyMatch(v -> v.getVin().equals(vin));
    }

    private boolean isWorkshopExist(String name) {
        return workshops.stream().anyMatch(w -> w.getName().equals(name));
    }
}