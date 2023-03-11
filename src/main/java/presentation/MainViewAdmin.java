package presentation;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "Admin")
@PageTitle("Admin Page")
public class MainViewAdmin extends VerticalLayout {
    private TextField userID = new TextField("User ID");
    private TextField eventID = new TextField("Event ID");
    private Button buttonUser = new Button("Modify user");
    private Button buttonEvent = new Button("Modify event");

    public MainViewAdmin() {
        getStyle().set("background", "url(https://iili.io/HcFwSLv.png)");
        HorizontalLayout hl = new HorizontalLayout();

        buttonEvent.getStyle().set("background-color", "#EF6C33");
        buttonEvent.getStyle().set("color", "#E1DDDB");
        buttonEvent.getStyle().set("font-size", "20px");
        buttonEvent.getStyle().set("padding", "20px 40px");
        buttonEvent.getStyle().set("border-radius", "0px");

        buttonUser.getStyle().set("background-color", "#EF6C33");
        buttonUser.getStyle().set("color", "#E1DDDB");
        buttonUser.getStyle().set("font-size", "20px");
        buttonUser.getStyle().set("padding", "20px 40px");
        buttonUser.getStyle().set("border-radius", "0px");

        userID.setValue("1");
        eventID.setValue("1");

        hl.add(new UserModifyView(userID.getValue()));


        buttonUser.addClickListener( l -> {
            hl.removeAll();
            hl.add(new UserModifyView(userID.getValue()));
        });

        buttonEvent.addClickListener( l -> {
            hl.removeAll();
            hl.add(new EventModifyView(eventID.getValue()));
        });

        hl.setAlignItems(Alignment.BASELINE);
        hl.setJustifyContentMode(JustifyContentMode.CENTER);

        hl.setHeightFull();
        hl.setWidthFull();

        add(userID, eventID, buttonEvent, buttonUser, hl);

        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.AROUND);
    }
}
