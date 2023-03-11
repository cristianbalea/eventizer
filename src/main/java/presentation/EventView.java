package presentation;

import businesslogic.EventBL;
import businesslogic.ProfileBL;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import model.Event;
import model.User;

public class EventView extends VerticalLayout {
    EventBL eventBL = new EventBL();
    ProfileBL profileBL = new ProfileBL();
    Button participate = new Button("Participate");
    Button feedback = new Button("Add feedback");
    Label event = new Label("");
    TextField f = new TextField("");
    Image img;
    Notification notification = new Notification();
    int evID;

    public EventView(int id, String userID, boolean p) {
        evID = id;
        img = new Image(eventBL.getEventImg(id), "event");
        img.getStyle().set("height", "200px");

        participate.getStyle().set("background-color", "#EF6C33");
        participate.getStyle().set("color", "#E1DDDB");
        participate.getStyle().set("font-size", "15px");
        participate.getStyle().set("padding", "10px 30px");
        participate.getStyle().set("border-radius", "0px");

        participate.addClickListener( l -> {
            int i = Integer.parseInt(userID);
           profileBL.participate(new User(i, "", "","", "", ""), new Event(id, "", "", 0, 0));
           notification.setText("Event join successful!");
           notification.setPosition(Notification.Position.BOTTOM_CENTER);
           notification.setDuration(2000);
        });

        feedback.getStyle().set("background-color", "#EF6C33");
        feedback.getStyle().set("color", "#E1DDDB");
        feedback.getStyle().set("font-size", "15px");
        feedback.getStyle().set("padding", "10px 30px");
        feedback.getStyle().set("border-radius", "0px");

        event.setText(eventBL.getEventName(id));
        event.getStyle().set("font-family", "Georgia");
        event.getStyle().set("font-size", "20px");
        event.getStyle().set("color", "#0C4A60");

        f.getStyle().set("font-family", "Georgia");
        f.getStyle().set("font-size", "15px");
        f.getStyle().set("color", "#0C4A60");

        int i = Integer.parseInt(userID);

        User u = new User(i, "", "", "", "", "");
        Event e = new Event(id, "", "", 0, 0);
        f.setValue(profileBL.getFeedback(u, e));

        add(event, img, new Label(eventBL.getEventDescription(id)));
        if (!p) {
            add(participate);
        }

        if(p) {
            add(f, feedback);
        }

        feedback.addClickListener( l -> {
             profileBL.feedback(u, e, f.getValue());
             f.setValue(profileBL.getFeedback(u, e));
        });

        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
    }

    public Button getParticipate() {
        return participate;
    }
    public int getEventID() {
       return evID;
    }
}
