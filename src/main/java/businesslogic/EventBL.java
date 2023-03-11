package businesslogic;

import dao.EventDAO;
import model.Event;
import model.User;

import java.util.ArrayList;

public class EventBL {
    private EventDAO eventDAO = new EventDAO();
    private static EventBL eventBL = new EventBL();

    public EventBL() {

    }

    public String getEventImg(int id) {
        return eventDAO.getEventImg(id);
    }

    public ArrayList<Integer> getEvents() {
        return eventDAO.getEvents();
    }

    public String getEventName(int id) {
        return eventDAO.getEventName(id);
    }
    public String getEventDescription(int id) {
        return eventDAO.getDescription(id);
    }

    public Event getEvent(String eventID) {
        return eventDAO.getEvent(eventID);
    }

    public void modifyEvent(Event e) {
        eventDAO.modifyEvent(e);
    }

    public void createEvent(Event e, User u) {
        eventDAO.createEvent(e);
        //eventDAO.createEventHelp(e, u);
    }
}
