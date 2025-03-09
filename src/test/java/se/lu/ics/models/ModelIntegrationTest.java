package se.lu.ics.models;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

public class ModelIntegrationTest {

    private AppModel model;

    @BeforeEach
    void setUp() {
        model = new AppModel();
    }

    @Test
    void testAddVehicle() {
        int initialSize = model.getVehicles().size();
        Vehicle v = new Vehicle("TestVan", VehicleType.VAN, 1200);
        boolean added = model.addVehicle(v);
        assertTrue(added);
        assertEquals(initialSize + 1, model.getVehicles().size());
        assertTrue(model.getVehicles().contains(v));
    }

    @Test
    void testRemoveVehicle() {
        Vehicle v = new Vehicle("RemovableTruck", VehicleType.LARGE_TRUCK, 3000);
        model.addVehicle(v);
        int initialSize = model.getVehicles().size();
        boolean removed = model.removeVehicle(v.getVin());
        assertTrue(removed);
        assertEquals(initialSize - 1, model.getVehicles().size());
    }

    @Test
    void testAddWorkshop() {
        int initialSize = model.getWorkshops().size();
        Workshop w = new Workshop("TestWorkshop", "123 Main Street", false);
        boolean added = model.addWorkshop(w);
        assertTrue(added);
        assertEquals(initialSize + 1, model.getWorkshops().size());
        assertTrue(model.getWorkshops().contains(w));
    }

    @Test
    void testAddMaintenanceSchedule() {
        Vehicle v = new Vehicle("MaintenanceTruck", VehicleType.LARGE_TRUCK, 3000);
        model.addVehicle(v);
        Workshop w = new Workshop("TruckStop", "Highway 66", false);
        model.addWorkshop(w);
        int initialSize = model.getMaintenanceSchedules().size();
        MaintenanceSchedule schedule = new MaintenanceSchedule(
            LocalDate.now().plusDays(7),
            "Oil Change",
            1500.0,
            w,
            v
        );
        boolean added = model.addMaintenanceSchedule(schedule);
        assertTrue(added);
        assertEquals(initialSize + 1, model.getMaintenanceSchedules().size());
    }

    @Test
    void testAddServiceEntry() {
        Vehicle v = new Vehicle("ServiceVan", VehicleType.VAN, 900);
        model.addVehicle(v);
        Workshop w = new Workshop("ServiceShop", "Main Street", false);
        model.addWorkshop(w);
        int initialSize = model.getServiceEntries().size();
        ServiceEntry entry = new ServiceEntry(
            LocalDate.now(),
            "Brake repair",
            250.0,
            2,
            w,
            v
        );
        boolean added = model.addServiceEntry(entry);
        assertTrue(added);
        assertEquals(initialSize + 1, model.getServiceEntries().size());
    }
}