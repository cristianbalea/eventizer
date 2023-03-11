package model;

public class Location {
    private int locationID;
    private String locationName;
    private String locationAddress;

    public Location(int locationID, String locationName, String locationAddress) {
        this.locationID = locationID;
        this.locationName = locationName;
        this.locationAddress = locationAddress;
    }

    public int getLocationID() {
        return locationID;
    }

    public void setLocationID(int locationID) {
        this.locationID = locationID;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationAddress() {
        return locationAddress;
    }

    public void setLocationAddress(String locationAddress) {
        this.locationAddress = locationAddress;
    }
}

