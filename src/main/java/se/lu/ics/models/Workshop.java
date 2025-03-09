package se.lu.ics.models;

public class Workshop {
    private String name;
    private String address;
    private boolean internal;

    public Workshop(String name, String address, boolean internal) {
        if (name == null || name.isEmpty() || address == null || address.isEmpty()) {
            throw new IllegalArgumentException("Workshop name and address cannot be empty.");
        }
        this.name = name;
        this.address = address;
        this.internal = internal;
    }

    public String getName() { return name; }
    public void setName(String name) { 
        if (name == null || name.isEmpty()) throw new IllegalArgumentException("Name cannot be empty.");
        this.name = name; 
    }
    public String getAddress() { return address; }
    public void setAddress(String address) { 
        if (address == null || address.isEmpty()) throw new IllegalArgumentException("Address cannot be empty.");
        this.address = address; 
    }
    public boolean isInternal() { return internal; }
    public void setInternal(boolean internal) { this.internal = internal; }
    @Override
    public String toString() {
        return "Workshop{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", internal=" + internal +
                '}';
    }
}