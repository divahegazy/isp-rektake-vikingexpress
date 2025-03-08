package se.lu.ics.models;

/**
 * Represents a workshop.
 */
public class Workshop {

    private String name;
    private String address;
    private boolean isInternal;

    public Workshop(String name, String address, boolean isInternal) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Workshop name cannot be empty.");
        }
        if (address == null || address.isEmpty()) {
            throw new IllegalArgumentException("Workshop address cannot be empty.");
        }
        this.name = name;
        this.address = address;
        this.isInternal = isInternal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isEmpty())
            throw new IllegalArgumentException("Workshop name cannot be empty.");
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        if (address == null || address.isEmpty())
            throw new IllegalArgumentException("Workshop address cannot be empty.");
        this.address = address;
    }

    public boolean isInternal() {
        return isInternal;
    }

    public void setInternal(boolean internal) {
        isInternal = internal;
    }

    public boolean isValid() {
        return name != null && !name.isEmpty()
                && address != null && !address.isEmpty();
    }

    @Override
    public String toString() {
        return String.format("Workshop [Name: %s, Address: %s, Type: %s]",
                name, address, isInternal ? "Internal" : "External");
    }
}