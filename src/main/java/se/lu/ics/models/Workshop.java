package se.lu.ics.models;

public class Workshop {
    private String name;
    private String address;
    private boolean isInternal;

    public Workshop(String name, String address, boolean isInternal) {
        this.name = name;
        this.address = address;
        this.isInternal = isInternal;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isInternal() {
        return isInternal;
    }
    public void setInternal(boolean isInternal) {
        this.isInternal = isInternal;
    }

    @Override
    public String toString() {
        return "Workshop[" + name + ", " + address + (isInternal ? ", internal" : ", external") + "]";
    }
}