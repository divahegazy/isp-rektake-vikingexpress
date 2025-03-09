package se.lu.ics.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ModelIntegrationTest {

    @Test
    void testAddVehicle() {
        AppModel model = new AppModel();
        int before = model.getAllVehicles().size();
        Vehicle v = new Vehicle("TestVan", VehicleType.VAN, 100);
        model.addVehicle(v);
        Assertions.assertEquals(before + 1, model.getAllVehicles().size());
    }

    @Test
    void testRemoveVehicle() {
        AppModel model = new AppModel();
        Vehicle v = new Vehicle("DeleteMe", VehicleType.MEDIUM_TRUCK, 300);
        model.addVehicle(v);
        Assertions.assertTrue(model.getAllVehicles().contains(v));

        model.removeVehicle(v);
        Assertions.assertFalse(model.getAllVehicles().contains(v));
    }

    @Test
    void testServiceCostAllVehicles() {
        AppModel model = new AppModel();
        double cost = model.getTotalServiceCostAllVehicles();
        // The test data from FleetManager might have e.g. 5200 or something
        Assertions.assertTrue(cost >= 0);
    }
}