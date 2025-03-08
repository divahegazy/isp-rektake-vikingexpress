package se.lu.ics.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ModelIntegrationTest {

    private AppModel appModel;

    @BeforeEach
    public void setUp() {
        appModel = new AppModel();
    }

    @Test
    public void testAddVehicle() {
        Vehicle vehicle1 = new Vehicle("Volvo FH16", VehicleType.LARGE_TRUCK, 20000);
        assertTrue(appModel.addVehicle(vehicle1), "[ERROR] Failed to add vehicle.");
        assertFalse(appModel.addVehicle(vehicle1), "[ERROR] Duplicate VIN should not be allowed.");
    }

    @Test
    public void testAddWorkshop() {
        Workshop workshop = new Workshop("Express Repairs", "123 Main St", true);
        assertTrue(appModel.addWorkshop(workshop), "[ERROR] Failed to add workshop.");
        assertFalse(appModel.addWorkshop(workshop), "[ERROR] Duplicate workshop name should not be allowed.");
    }

    @Test
    public void testAddServiceEntry() {
        Vehicle vehicle = new Vehicle("Volvo FH16", VehicleType.LARGE_TRUCK, 20000);
        Workshop workshop = new Workshop("Express Repairs", "123 Main St", true);
        appModel.addVehicle(vehicle);
        appModel.addWorkshop(workshop);
        
        ServiceEntry validEntry = new ServiceEntry(LocalDate.now(), "Oil Change", 250.0, 2, workshop, vehicle);
        ServiceEntry invalidEntry = new ServiceEntry(LocalDate.now(), "Brake Replacement", -100.0, 1, workshop, vehicle);
        
        assertTrue(appModel.addServiceEntry(validEntry), "[ERROR] Service entry should be added successfully.");
        assertFalse(appModel.addServiceEntry(invalidEntry), "[ERROR] Negative cost should not be allowed.");
    }

    @Test
    public void testCostCalculations() {
        Vehicle vehicle = new Vehicle("Volvo FH16", VehicleType.LARGE_TRUCK, 20000);
        Workshop workshop = new Workshop("Express Repairs", "123 Main St", true);
        appModel.addVehicle(vehicle);
        appModel.addWorkshop(workshop);
        
        ServiceEntry entry = new ServiceEntry(LocalDate.now(), "Oil Change", 250.0, 2, workshop, vehicle);
        appModel.addServiceEntry(entry);
        
        assertEquals(250.0, appModel.getTotalServiceCost(vehicle.getVin()), "[ERROR] Total service cost should be 250.0.");
        assertEquals(250.0, appModel.getAverageServiceCost(), "[ERROR] Average service cost should be 250.0.");
    }

    @Test
    public void testCostWarnings() {
        Vehicle vehicle = new Vehicle("Volvo FH16", VehicleType.LARGE_TRUCK, 20000);
        Workshop workshop = new Workshop("Express Repairs", "123 Main St", true);
        appModel.addVehicle(vehicle);
        appModel.addWorkshop(workshop);
        
        ServiceEntry entry = new ServiceEntry(LocalDate.now(), "Engine Overhaul", 120000.0, 5, workshop, vehicle);
        appModel.addServiceEntry(entry);
        
        assertTrue(appModel.checkServiceCostWarning(vehicle.getVin()), "[ERROR] Service cost warning should be triggered.");
    }

    @Test
    public void testDecommissionWarning() {
        Vehicle vehicle = new Vehicle("Volvo FH16", VehicleType.LARGE_TRUCK, 20000);
        appModel.addVehicle(vehicle);
        
        for (int i = 0; i < 101; i++) {
            vehicle.replaceParts(1);
        }
        
        assertTrue(appModel.checkDecommissionWarning(vehicle.getVin()), "[ERROR] Decommission warning should be triggered.");
    }

    @Test
    public void testValidationMethods() {
        Vehicle vehicle = new Vehicle("Volvo FH16", VehicleType.LARGE_TRUCK, 20000);
        Workshop workshop = new Workshop("Express Repairs", "123 Main St", true);
        appModel.addVehicle(vehicle);
        appModel.addWorkshop(workshop);
        
        assertTrue(appModel.isVehicleExist(vehicle.getVin()), "[ERROR] Vehicle should exist.");
        assertTrue(appModel.isWorkshopExist(workshop.getName()), "[ERROR] Workshop should exist.");
    }

    @Test
    public void testRemoveMethods() {
        Vehicle vehicle = new Vehicle("Volvo FH16", VehicleType.LARGE_TRUCK, 20000);
        Workshop workshop = new Workshop("Express Repairs", "123 Main St", true);
        appModel.addVehicle(vehicle);
        appModel.addWorkshop(workshop);
        
        assertTrue(appModel.removeVehicle(vehicle.getVin()), "[ERROR] Vehicle should be removed.");
        assertTrue(appModel.removeWorkshop(workshop.getName()), "[ERROR] Workshop should be removed.");
    }
}
