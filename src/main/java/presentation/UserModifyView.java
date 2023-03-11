package presentation;

import businesslogic.ProfileBL;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import model.User;

import com.vaadin.flow.component.notification.Notification;

public class UserModifyView extends VerticalLayout {
    ProfileBL profileBL = new ProfileBL();
    TextField username = new TextField("Name");
    TextField email = new TextField("E-mail");
    TextField phone = new TextField("Phone number");
    PasswordField password = new PasswordField("Password");
    Button edit = new Button("Save changes");
    Notification notification = new Notification();

    public UserModifyView(String userID) {
        User u = profileBL.getUser(userID);

        add(username, email, phone, password, edit, notification);

        username.setValue(u.getUsername());
        email.setValue(u.getEmail());
        phone.setValue(u.getPhone());
        password.setValue(u.getPassword());

        notification.setPosition(Notification.Position.BOTTOM_CENTER);
        notification.setDuration(2000);
        notification.setText("Changes applied successfully");

        edit.addClickListener( e -> {
            u.setUsername(username.getValue());
            u.setEmail(email.getValue());
            u.setPhone(phone.getValue());
            u.setPassword(password.getValue());

            profileBL.modifyUser(u);
            notification.open();

        });

        setWidth("600px");
        setHeight("500px");

        setAlignItems(Alignment.STRETCH);
        setJustifyContentMode(JustifyContentMode.CENTER);
    }
}
