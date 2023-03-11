package businesslogic;

import dao.ProfileDAO;
import model.Event;
import model.User;

import java.sql.SQLException;
import java.util.ArrayList;

public class ProfileBL {

    private ProfileDAO profile;
    private static ProfileBL profileLogic = new ProfileBL();

    public ProfileBL(){
        profile = new ProfileDAO();
    }

    public boolean updatePassword(int id,String password) throws SQLException {
        return profile.updatePassword(id,password);
    }

    public boolean updateName(int id,String name) throws SQLException {
        return profile.updateName(id,name);
    }

    public String getEmailAddress(int id) {
        return profile.getEmailAddress(id);
    }

    public String getUsername(String id) {
        return profile.getUsername(id);
    }

    public String getRole(int id) {
        return profile.getRole(id);
    }

    public int getID(String email){
        return profile.getID(email);
    }

    public static ProfileBL getProfileLogic() {
        return profileLogic;
    }

    public User getUser(String userID) {
        return profile.getUser(userID);
    }

    public void modifyUser(User u) {
        profile.modifyUser(u);
    }

    public void participate(User u, Event e) {
        profile.participate(u, e);
    }

    public void feedback(User u, Event e, String feed) {
        profile.feedback(u, e, feed);
    }

    public String getFeedback(User u, Event e) {
        return profile.getFeedback(u, e);
    }

    public ArrayList<Integer> getEvents(String userID) {
        return profile.getEvents(userID);
    }
}

