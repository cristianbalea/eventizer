package businesslogic;

import dao.RegisterDAO;

public class RegisterBL {
    RegisterDAO registerDAO = new RegisterDAO();

    public boolean insertUser(String username, String email, String phone,String password, String role) {
        return registerDAO.insertUser(username, email, phone, password, role);
    }
}
