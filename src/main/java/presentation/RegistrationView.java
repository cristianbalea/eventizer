package presentation;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "Registration")
@PageTitle("Register")
public class RegistrationView extends VerticalLayout {

    public RegistrationView(){

        RegistrationForm registrationForm = new RegistrationForm();
        add(registrationForm);
        setHeightFull();
        setWidthFull();
        getStyle().set("background", "url(https://iili.io/HcFwSLv.png)");
        registrationForm.getElement().getThemeList().add("dark");

        setHorizontalComponentAlignment(Alignment.CENTER, registrationForm);
        setJustifyContentMode(JustifyContentMode.CENTER);
    }
}