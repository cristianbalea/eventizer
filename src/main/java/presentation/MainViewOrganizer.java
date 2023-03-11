package presentation;

import businesslogic.EventBL;
import businesslogic.ProfileBL;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import model.Event;
import model.User;

@Route("organizer/:organizerID/")
@PageTitle("Organize an e event")
public class MainViewOrganizer extends VerticalLayout implements BeforeEnterObserver {
    EventBL eventBL = new EventBL();
    ProfileBL profileBL = new ProfileBL();
    H3 hello = new H3();
    Label add = new Label("Add a new event");
    TextField eventName = new TextField("Event name");
    TextField description = new TextField("Description");
    TextField eventImg = new TextField("Event Image");
    Button edit = new Button("Add event");
    Notification notification = new Notification();
    User u = new User();
    private Button modify = new Button("Modify account info");
    Dialog dialog = new Dialog();

    public String userID;

    public MainViewOrganizer() {
        Event e = new Event(0, "", "",
                0, 0, "");

        getStyle().set("background", "url(https://iili.io/HcFwSLv.png)");

        add.getStyle().set("font-family", "Georgia");
        add.getStyle().set("font-size", "32px");

        eventName.setMinWidth("400px");
        description.setMinWidth("400px");
        eventImg.setMinWidth("400px");

        notification.setPosition(Notification.Position.BOTTOM_CENTER);
        notification.setDuration(2000);
        notification.setText("Event added successfully");

        edit.addClickListener( l -> {
            e.setEventName(eventName.getValue());
            e.setDescription(description.getValue());
            e.setParticipantsNumber(0);
            e.setEventImg(eventImg.getValue());

            eventBL.createEvent(e, u);
            notification.open();

        });

        setWidthFull();
        setHeightFull();

        add(hello, add, eventName, description, eventImg, edit, notification, modify);
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
    }


    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        userID = beforeEnterEvent.getRouteParameters().get("organizerID").orElse("ohno");
        configurePage(userID);
    }

    private void configurePage(String userID) {
        hello.setText("Hello, " + profileBL.getUser(userID).getUsername() + "!");
        hello.getStyle().set("font-family", "Georgia");
        hello.getStyle().set("font-size", "48px");
        u.setUserID(Integer.parseInt(userID));

        modify.addClickListener( l -> {
            Button cl = new Button("Close", j -> dialog.close());

            dialog.setHeaderTitle("Modify account details");
            dialog.add(new UserModifyView(userID));
            dialog.getFooter().add(cl);

            dialog.open();
        });
    }
}
