package se.lu.ics.models;

import java.util.List;

public class AppModel {
    private FleetManager fleetManager;

    public AppModel() {
        this.fleetManager = new FleetManager();
    }

    // --- Vehicle Management ---
    public boolean addVehicle(Vehicle vehicle) {
        return fleetManager.addVehicle(vehicle);
    }

    public Vehicle getVehicleByVin(String vin) {
        return fleetManager.getVehicleByVin(vin);
    }

    // Note: removeVehicle expects a VIN (String) according to your FleetManager implementation.
    public boolean removeVehicle(String vin) {
        return fleetManager.removeVehicle(vin);
    }

    public List<Vehicle> getVehicles() {
        return fleetManager.getAllVehicles();
    }

    // --- Workshop Management ---
    public boolean addWorkshop(Workshop workshop) {
        return fleetManager.addWorkshop(workshop);
    }

    public Workshop getWorkshopByName(String name) {
        return fleetManager.getWorkshopByName(name);
    }

    public boolean removeWorkshop(String name) {
        return fleetManager.removeWorkshop(name);
    }

    public List<Workshop> getWorkshops() {
        return fleetManager.getAllWorkshops();
    }

    // --- Service Entry Management ---
    public boolean addServiceEntry(ServiceEntry entry) {
        return fleetManager.addServiceEntry(entry);
    }

    public boolean removeServiceEntry(ServiceEntry entry) {
        return fleetManager.removeServiceEntry(entry);
    }

    public List<ServiceEntry> getServiceEntries() {
        return fleetManager.getAllServiceEntries();
    }

    // --- Maintenance Schedule Management ---
    public boolean addMaintenanceSchedule(MaintenanceSchedule schedule) {
        return fleetManager.addMaintenanceSchedule(schedule);
    }

    public boolean removeMaintenanceSchedule(MaintenanceSchedule schedule) {
        return fleetManager.removeMaintenanceSchedule(schedule);
    }

    public List<MaintenanceSchedule> getMaintenanceSchedules() {
        return fleetManager.getAllMaintenanceSchedules();
    }

    // --- Cost Calculations (for Reports, level B) ---
    public double getTotalServiceCost(String vin) {
        return fleetManager.getTotalServiceCost(vin);
    }

    public double getAverageServiceCost() {
        return fleetManager.getAverageServiceCost();
    }

    public boolean checkServiceCostWarning(String vin) {
        return fleetManager.checkServiceCostWarning(vin);
    }

    public boolean checkDecommissionWarning(String vin) {
        return fleetManager.checkDecommissionWarning(vin);
    }
}