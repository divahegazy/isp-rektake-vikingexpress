package se.lu.ics.models;

import java.util.ArrayList;
import java.util.List;

public class FleetManager {
    private final List<Vehicle> vehicles = new ArrayList<>();
    private final List<Workshop> workshops = new ArrayList<>();
    private final List<ServiceEntry> serviceEntries = new ArrayList<>();
    private final List<MaintenanceSchedule> maintenanceSchedules = new ArrayList<>();


    // Add vehicle
    public boolean addVehicle(Vehicle vehicle) {
        if (isVehicleExist(vehicle.getVin())) {
            return false;
        }
        vehicles.add(vehicle);
        return true;
    }

    // Remove vehicle by VIN
    public boolean removeVehicle(String vin) {
        return vehicles.removeIf(v -> v.getVin().equals(vin));
    }

    // isVehicleExist
    public boolean isVehicleExist(String vin) {
        return vehicles.stream().anyMatch(v -> v.getVin().equals(vin));
    }

    // getVehicleByVin
    public Vehicle getVehicleByVin(String vin) {
        return vehicles.stream()
                .filter(v -> v.getVin().equals(vin))
                .findFirst()
                .orElse(null);
    }

    // getAllVehicles
    public List<Vehicle> getAllVehicles() {
        return vehicles;
    }

    // Workshop
    public boolean addWorkshop(Workshop w) {
        if (isWorkshopExist(w.getName())) {
            return false;
        }
        workshops.add(w);
        return true;
    }

    public boolean removeWorkshop(String name) {
        return workshops.removeIf(w -> w.getName().equals(name));
    }

    public boolean isWorkshopExist(String name) {
        return workshops.stream().anyMatch(w -> w.getName().equals(name));
    }

    public Workshop getWorkshopByName(String name) {
        return workshops.stream()
                .filter(w -> w.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public List<Workshop> getAllWorkshops() {
        return workshops;
    }

    // ServiceEntry
    public boolean addServiceEntry(ServiceEntry entry) {
        if (entry.getCost() < 0) return false;
        serviceEntries.add(entry);
        return true;
    }

    public boolean removeServiceEntry(ServiceEntry entry) {
        return serviceEntries.remove(entry);
    }

    public List<ServiceEntry> getAllServiceEntries() {
        return serviceEntries;
    }

    // Maintenance
    public boolean addMaintenanceSchedule(MaintenanceSchedule ms) {
        if (ms.getCost() < 0) return false;
        maintenanceSchedules.add(ms);
        return true;
    }

    public boolean removeMaintenanceSchedule(MaintenanceSchedule ms) {
        return maintenanceSchedules.remove(ms);
    }

    public List<MaintenanceSchedule> getAllMaintenanceSchedules() {
        return maintenanceSchedules;
    }

    // cost
    public double getTotalServiceCost(String vin) {
        return serviceEntries.stream()
                .filter(e -> e.getVehicleVin().equals(vin))
                .mapToDouble(ServiceEntry::getCost)
                .sum();
    }

    public double getAverageServiceCost() {
        return serviceEntries.stream()
                .mapToDouble(ServiceEntry::getCost)
                .average()
                .orElse(0.0);
    }

    public boolean checkServiceCostWarning(String vin) {
        return getTotalServiceCost(vin) > 100000;
    }

    public boolean checkDecommissionWarning(String vin) {
        Vehicle v = getVehicleByVin(vin);
        return (v != null) && v.isDecommissioned();
    } // checks if the vehicle is decommissioned


    
}