package presentation;

import businesslogic.EventBL;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import model.Event;

import com.vaadin.flow.component.notification.Notification;

public class EventModifyView extends VerticalLayout {
    EventBL eventBL = new EventBL();
    TextField eventName = new TextField("Event name");
    TextField description = new TextField("Description");
    TextField participantsNumber = new TextField("Participants number");
    TextField eventImg = new TextField("Event Image");
    Button edit = new Button("Save changes");
    Notification notification = new Notification();

    public EventModifyView(String eventID) {
        Event e = eventBL.getEvent(eventID);

        add(eventName, description, participantsNumber, eventImg, edit, notification);

        eventName.setValue(e.getEventName());
        description.setValue(e.getDescription());
        participantsNumber.setValue(String.valueOf(e.getParticipantsNumber()));
        eventImg.setValue(e.getEventImg());

        notification.setPosition(Notification.Position.BOTTOM_CENTER);
        notification.setDuration(2000);
        notification.setText("Changes applied successfully");

        edit.addClickListener( l -> {
            e.setEventName(eventName.getValue());
            e.setDescription(description.getValue());
            e.setParticipantsNumber(Integer.parseInt(participantsNumber.getValue()));
            e.setEventImg(eventImg.getValue());

            eventBL.modifyEvent(e);
            notification.open();

        });

        setWidth("600px");
        setHeight("500px");

        setAlignItems(Alignment.STRETCH);
        setJustifyContentMode(JustifyContentMode.CENTER);
    }
}