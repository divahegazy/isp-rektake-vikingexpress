package se.lu.ics.models;

/**
 * Enum for vehicle types.
 */
public enum VehicleType {
    LARGE_TRUCK("Largetruck"),
    MEDIUM_TRUCK("Mediumtruck"),
    VAN("Van");

    private final String displayName;

    VehicleType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}