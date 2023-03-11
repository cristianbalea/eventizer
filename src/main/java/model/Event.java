package model;

public class Event {
    private int eventID;
    private String eventName;
    private String description;
    private int participantsNumber;
    private int locationID;
    private String eventImg;

    public Event(int eventID, String eventName, String description, int participantsNumber, int locationID, String eventImg) {
        this.eventID = eventID;
        this.eventName = eventName;
        this.description = description;
        this.participantsNumber = participantsNumber;
        this.locationID = locationID;
        this.eventImg = eventImg;
    }

    public Event(int eventID, String eventName, String description, int participantsNumber, int locationID) {
        this.eventID = eventID;
        this.eventName = eventName;
        this.description = description;
        this.participantsNumber = participantsNumber;
        this.locationID = locationID;
    }

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getParticipantsNumber() {
        return participantsNumber;
    }

    public void setParticipantsNumber(int participantsNumber) {
        this.participantsNumber = participantsNumber;
    }

    public int getLocationID() {
        return locationID;
    }

    public void setLocationID(int locationID) {
        this.locationID = locationID;
    }

    public String getEventImg() {
        return eventImg;
    }

    public void setEventImg(String eventImg) {
        this.eventImg = eventImg;
    }
}
