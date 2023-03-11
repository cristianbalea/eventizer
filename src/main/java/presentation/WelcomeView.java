package presentation;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

@Route("")
@PageTitle("Eventizer - welcome!")
public class WelcomeView extends VerticalLayout {
    Button btnLogin = new Button("Login");
    Button btnRegister = new Button("Register");
    Label ev = new Label("Eventizer - we build your events");

    public WelcomeView() {
        getStyle().set("background", "url(https://iili.io/HcX2eu2.png)");
        Image img = new Image("https://www.verywellmind.com/thmb/QGS470MKXc4IEqpbQl3qm1bUsuQ=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/GettyImages-1057500046-f7e673d3a91546b0bd419c5d8336b2e0.jpg", "social-event");
        img.getStyle().set("height", "500px");

        btnLogin.addClickListener(e -> {
            UI.getCurrent().navigate(LoginView.class);
        });

        btnRegister.addClickListener(e -> {
            UI.getCurrent().navigate(RegistrationView.class);
        });

        setWidth("100%");
        setHeight("100%");

        btnRegister.getStyle().set("background-color", "#EF6C33");
        btnLogin.getStyle().set("background-color", "#EF6C33");
        btnRegister.getStyle().set("color", "#E1DDDB");
        btnLogin.getStyle().set("color", "#E1DDDB");
        btnRegister.getStyle().set("font-size", "30px");
        btnLogin.getStyle().set("font-size", "30px");
        btnRegister.getStyle().set("padding", "30px 50px");
        btnLogin.getStyle().set("padding", "30px 50px");
        btnLogin.getStyle().set("border-radius", "0px");
        btnRegister.getStyle().set("border-radius", "0px");
        btnRegister.getStyle().set("border-radius", "0px");

        ev.getStyle().set("font-family", "Georgia");
        ev.getStyle().set("font-size", "48px");
        ev.getStyle().set("color", "#0C4A60");

        HorizontalLayout buttons = new HorizontalLayout();

        buttons.add(btnLogin, btnRegister);

        add(ev, buttons, img);

        setAlignItems(Alignment.START);
        setJustifyContentMode(JustifyContentMode.AROUND);
    }
}
