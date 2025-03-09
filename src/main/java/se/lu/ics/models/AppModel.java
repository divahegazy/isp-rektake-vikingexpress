package se.lu.ics.models;

import java.util.List;

public class AppModel {

    private final FleetManager fleetManager;

    public AppModel() {
        this.fleetManager = new FleetManager();
    }

    // Vehicles
    public boolean addVehicle(Vehicle v) {
        return fleetManager.addVehicle(v);
    }

    public boolean removeVehicle(Vehicle v) {
        return fleetManager.removeVehicle(v.getVin());
    }

    public Vehicle getVehicleByVin(String vin) {
        return fleetManager.getVehicleByVin(vin);
    }

    public List<Vehicle> getAllVehicles() {
        return fleetManager.getAllVehicles();
    }

    public boolean isVehicleExist(String vin) {
        return fleetManager.isVehicleExist(vin);
    }

    // Workshops
    public boolean addWorkshop(Workshop ws) {
        return fleetManager.addWorkshop(ws);
    }

    public boolean removeWorkshop(Workshop ws) {
        return fleetManager.removeWorkshop(ws.getName());
    }

    public Workshop getWorkshopByName(String name) {
        return fleetManager.getWorkshopByName(name);
    }

    public List<Workshop> getAllWorkshops() {
        return fleetManager.getAllWorkshops();
    }

    // Service Entries
    public boolean addServiceEntry(ServiceEntry entry) {
        return fleetManager.addServiceEntry(entry);
    }

    public boolean removeServiceEntry(ServiceEntry entry) {
        return fleetManager.removeServiceEntry(entry);
    }

    public List<ServiceEntry> getAllServiceEntries() {
        return fleetManager.getAllServiceEntries();
    }

    // Maintenance
    public boolean addMaintenanceSchedule(MaintenanceSchedule ms) {
        return fleetManager.addMaintenanceSchedule(ms);
    }

    public boolean removeMaintenanceSchedule(MaintenanceSchedule ms) {
        return fleetManager.removeMaintenanceSchedule(ms);
    }

    public List<MaintenanceSchedule> getAllMaintenanceSchedules() {
        return fleetManager.getAllMaintenanceSchedules();
    }

    // Costs
    public double getTotalServiceCost(String vin) {
        return fleetManager.getTotalServiceCost(vin);
    }

    public double getTotalServiceCostAllVehicles() {
        // sum all vehicles
        return fleetManager.getAllVehicles().stream()
                .mapToDouble(v -> getTotalServiceCost(v.getVin()))
                .sum();
    }

    public double getAverageServiceCost() {
        return fleetManager.getAverageServiceCost();
    }

    // Warnings
    public boolean checkServiceCostWarning(String vin) {
        return fleetManager.checkServiceCostWarning(vin);
    }

    public boolean checkDecommissionWarning(String vin) {
        return fleetManager.checkDecommissionWarning(vin);
    }
}