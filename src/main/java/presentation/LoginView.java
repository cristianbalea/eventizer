package presentation;

import businesslogic.LoginBL;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteParameters;

import java.util.Locale;

@Route(value = "Login")
@PageTitle("Login")
public class LoginView extends VerticalLayout {

    LoginForm loginForm = new LoginForm();
    Button registerButton = new Button("Register");

    LoginBL login = LoginBL.getLoginLogic();

    public LoginView() {
        getStyle().set("background", "url(https://iili.io/HcFwSLv.png)");
        add(loginForm);
        loginForm.getElement().getThemeList().add("dark");

        setWidth("100%");
        setHeight("100%");

        loginForm.addLoginListener(e -> {
            boolean isAuthenticated = login.authenticate(e.getUsername(), e.getPassword());
            if (isAuthenticated) {
                login.send();
                int id = login.getID(e.getUsername());
                String role = login.getRole(String.valueOf(id));
                if (role.equalsIgnoreCase("client")) {
                    UI.getCurrent().navigate(MainView.class, new RouteParameters("userID", String.valueOf(id)));
                } else if (role.equalsIgnoreCase("organizer")) {
                    UI.getCurrent().navigate(MainViewOrganizer.class, new RouteParameters("organizerID", String.valueOf(id)));
                } else if (role.equalsIgnoreCase("admin")) {
                    UI.getCurrent().navigate(MainViewAdmin.class);
                }
            } else {
                loginForm.setError(true);
            }
        });

        registerButton.addClickListener(e -> {
           UI.getCurrent().navigate(RegistrationView.class);
        });

        registerButton.setWidth("19%");
        registerButton.getStyle().set("background-color","blue");
        registerButton.getStyle().set("color","white");

        // add elements
        add(loginForm);
        add(registerButton);
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
    }
}