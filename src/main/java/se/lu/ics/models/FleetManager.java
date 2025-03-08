package se.lu.ics.models;

import java.util.ArrayList;
import java.util.List;

public class FleetManager {
    private List<Vehicle> vehicles = new ArrayList<>();
    private List<Workshop> workshops = new ArrayList<>();
    private List<ServiceEntry> serviceEntries = new ArrayList<>();
    private List<MaintenanceSchedule> maintenanceSchedules = new ArrayList<>();

    // --- Add Vehicle ---
    public boolean addVehicle(Vehicle vehicle) {
        if (isVehicleExist(vehicle.getVin())) {
            System.out.println("[ERROR] Duplicate VIN is not allowed: " + vehicle.getVin());
            return false;
        }
        vehicles.add(vehicle);
        System.out.println("[DEBUG] Vehicle added: " + vehicle);
        return true;
    }

    // --- Get Vehicle by VIN ---
    public Vehicle getVehicleByVin(String vin) {
        return vehicles.stream()
                .filter(v -> v.getVin().equals(vin))
                .findFirst()
                .orElse(null);
    }

    // --- Add Workshop ---
    public boolean addWorkshop(Workshop workshop) {
        if (isWorkshopExist(workshop.getName())) {
            System.out.println("[ERROR] Workshop already exists: " + workshop.getName());
            return false;
        }
        workshops.add(workshop);
        System.out.println("[DEBUG] Workshop added: " + workshop);
        return true;
    }

    // --- Get Workshop by Name ---
    public Workshop getWorkshopByName(String name) {
        return workshops.stream()
                .filter(w -> w.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    // --- Add Service Entry ---
    public boolean addServiceEntry(ServiceEntry entry) {
        if (entry.getCost() < 0) {
            System.out.println("[ERROR] Negative cost is not allowed.");
            return false;
        }
        serviceEntries.add(entry);
        System.out.println("[DEBUG] Service entry added: " + entry);
        return true;
    }

    // --- Add Maintenance Schedule ---
    public boolean addMaintenanceSchedule(MaintenanceSchedule schedule) {
        maintenanceSchedules.add(schedule);
        return true;
    }

    // --- Get Average Service Cost ---
    public double getAverageServiceCost() {
        return serviceEntries.stream()
                .mapToDouble(ServiceEntry::getCost)
                .average()
                .orElse(0.0);
    }

    // --- Check Service Cost Warning ---
    public boolean checkServiceCostWarning(String vin) {
        double totalCost = getTotalServiceCost(vin);
        return totalCost > 10000;  // Example threshold
    }

    // --- Check Decommission Warning ---
    public boolean checkDecommissionWarning(String vin) {
        Vehicle vehicle = getVehicleByVin(vin);
        return vehicle != null && vehicle.isDecommissioned();  // Example condition
    }

    // --- Remove Methods ---
    public boolean removeVehicle(String vin) {
        return vehicles.removeIf(v -> v.getVin().equals(vin));
    }

    public boolean removeWorkshop(String name) {
        return workshops.removeIf(w -> w.getName().equals(name));
    }

    public boolean removeServiceEntry(ServiceEntry entry) {
        return serviceEntries.remove(entry);
    }

    public boolean removeMaintenanceSchedule(MaintenanceSchedule schedule) {
        return maintenanceSchedules.remove(schedule);
    }

    // --- Get All Maintenance Schedules ---
    public List<MaintenanceSchedule> getAllMaintenanceSchedules() {
        return maintenanceSchedules;
    }

    // --- Get Total Service Cost ---
    public double getTotalServiceCost(String vin) {
        return serviceEntries.stream()
                .filter(e -> e.getVehicleVin().equals(vin))
                .mapToDouble(ServiceEntry::getCost)
                .sum();
    }

    // --- Check if Vehicle Exists ---
    public boolean isVehicleExist(String vin) {
        return vehicles.stream().anyMatch(v -> v.getVin().equals(vin));
    }

    // --- Check if Workshop Exists ---
    public boolean isWorkshopExist(String name) {
        return workshops.stream().anyMatch(w -> w.getName().equals(name));
    }

    // --- Get All Vehicles ---
    public List<Vehicle> getAllVehicles() {
        return vehicles;
    }

    // --- Get All Workshops ---
    public List<Workshop> getAllWorkshops() {
        return workshops;
    }

    // --- Get All Service Entries by Vehicle ---
    public List<ServiceEntry> getServiceEntriesByVehicle(String vin) {
        // Return a filtered list of serviceEntries that match the VIN
        return serviceEntries.stream()
                .filter(e -> e.getVehicleVin().equals(vin))
                .toList();
    }

    // --- Get All Service Entries (NEW METHOD) ---
    public List<ServiceEntry> getAllServiceEntries() {
        return serviceEntries;
    }
}