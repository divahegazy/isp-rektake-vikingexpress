package se.lu.ics.models;

import java.util.ArrayList;
import java.util.List;

public class FleetManager {
    private final List<Vehicle> vehicles = new ArrayList<>();
    private final List<Workshop> workshops = new ArrayList<>();
    private final List<ServiceEntry> serviceEntries = new ArrayList<>();
    private final List<MaintenanceSchedule> maintenanceSchedules = new ArrayList<>();

    public FleetManager() {
        // Optionally add some test data
        addTestData();
    }

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
    }


    // test data
    private void addTestData() {
        Workshop w1 = new Workshop("ScaniaCentral", "Malmö 123", false);
        Workshop w2 = new Workshop("TruckFix", "Lund 45", true);
        Workshop w3 = new Workshop("SuperGarage", "Helsingborg 99", false);
        workshops.add(w1);
        workshops.add(w2);
        workshops.add(w3);

        Vehicle v1 = new Vehicle("Volvo FH16", VehicleType.LARGE_TRUCK, 5000);
        Vehicle v2 = new Vehicle("Mercedes Sprinter", VehicleType.VAN, 1200);
        Vehicle v3 = new Vehicle("Scania R500", VehicleType.MEDIUM_TRUCK, 3000);
        vehicles.add(v1);
        vehicles.add(v2);
        vehicles.add(v3);

        serviceEntries.add(new ServiceEntry(java.time.LocalDate.now(), "Engine check", 5000, 8, w1, v1));
        serviceEntries.add(new ServiceEntry(java.time.LocalDate.now(), "Oil change", 200, 2, w2, v2));

        maintenanceSchedules.add(new MaintenanceSchedule(java.time.LocalDate.now().plusDays(30), "Annual check", 1000, w1, v1));
        maintenanceSchedules.add(new MaintenanceSchedule(java.time.LocalDate.now().plusDays(10), "Tire rotation", 300, w3, v2));
    }
}