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

    public boolean removeVehicle(String vin) {
        return fleetManager.removeVehicle(vin);
    }

    public boolean isVehicleExist(String vin) {
        return fleetManager.isVehicleExist(vin);
    }

    public List<Vehicle> getAllVehicles() {
        return fleetManager.getAllVehicles();
    }

    // New Wrapper Method
    public List<Vehicle> getVehicles() {
        return getAllVehicles();
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

    public boolean isWorkshopExist(String name) {
        return fleetManager.isWorkshopExist(name);
    }

    public List<Workshop> getAllWorkshops() {
        return fleetManager.getAllWorkshops();
    }

    // New Wrapper Method
    public List<Workshop> getWorkshops() {
        return getAllWorkshops();
    }

    // --- Service Entry Management ---
    public boolean addServiceEntry(ServiceEntry entry) {
        return fleetManager.addServiceEntry(entry);
    }

    public boolean removeServiceEntry(ServiceEntry entry) {
        return fleetManager.removeServiceEntry(entry);
    }

    // **This must match the FleetManager method** 
    public List<ServiceEntry> getServiceEntriesByVehicle(String vin) {
        return fleetManager.getServiceEntriesByVehicle(vin);
    }

    public List<ServiceEntry> getAllServiceEntries() {
        return fleetManager.getAllServiceEntries();
    }

    // New Wrapper Method
    public List<ServiceEntry> getServiceEntries() {
        return getAllServiceEntries();
    }

    // --- Maintenance Schedule Management ---
    public boolean addMaintenanceSchedule(MaintenanceSchedule schedule) {
        return fleetManager.addMaintenanceSchedule(schedule);
    }

    public boolean removeMaintenanceSchedule(MaintenanceSchedule schedule) {
        return fleetManager.removeMaintenanceSchedule(schedule);
    }

    public List<MaintenanceSchedule> getAllMaintenanceSchedules() {
        return fleetManager.getAllMaintenanceSchedules();
    }

    public List<MaintenanceSchedule> getMaintenanceSchedules() {
        return getAllMaintenanceSchedules();
    }

    // --- Cost and Warnings ---
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