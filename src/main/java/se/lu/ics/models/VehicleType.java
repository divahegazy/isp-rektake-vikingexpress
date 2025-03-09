package se.lu.ics.models;

public enum VehicleType {
    LARGE_TRUCK("Large Truck"),
    MEDIUM_TRUCK("Medium Truck"),
    VAN("Van");
    
    private final String displayName;
    VehicleType(String displayName) { this.displayName = displayName; }
    public String getDisplayName() { return displayName; }
}