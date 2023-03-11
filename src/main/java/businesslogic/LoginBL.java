package businesslogic;

import dao.LoginDAO;
import presentation.MainView;

import java.util.Observable;

public class LoginBL extends Observable {

    private static LoginBL loginLogic = new LoginBL();
    private LoginDAO login;

    private LoginBL() {
        login = new LoginDAO();
    }

    public static LoginBL getLoginLogic() {
        return loginLogic;
    }

    public boolean authenticate(String email, String password) {
        return login.isUser(email, password);
    }

    public int getID(String email) {
        return login.findID(email);
    }

    public void send() {
        setChanged();
        addObserver(new MainView());
        notifyObservers();
    }

    public String getRole(String userID) {
        return login.getRole(userID);
    }
}
