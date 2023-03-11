package presentation;

import businesslogic.RegisterBL;
import com.vaadin.flow.component.HasValueAndElement;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;

import java.sql.SQLException;
import java.util.stream.Stream;

public class RegistrationForm extends VerticalLayout {

    Notification notification = new Notification();
    Dialog dialog = new Dialog();
    private H3 title = new H3("Register");
    private TextField name = new TextField("Name");
    private EmailField email = new EmailField("Email");
    private TextField phone = new TextField("Phone number");
    private PasswordField password = new PasswordField("Password");
    private Span errorMessageField = new Span();
    private Button submitButton = new Button("Sign Up");
    private HorizontalLayout emptySpace = new HorizontalLayout();
    private RadioButtonGroup<String> btnGroup = new RadioButtonGroup();


    public RegistrationForm() {
        setRequiredIndicatorVisible(name, name, email, password);

        submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        btnGroup.setItems("Client", "Organizer");
        btnGroup.setValue("Client");
        btnGroup.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);

        notification.setText("All fields must be completed!");
        notification.setPosition(Notification.Position.BOTTOM_CENTER);
        notification.setDuration(2000);

        Button log = new Button("Go to login page", l -> UI.getCurrent().navigate(LoginView.class));
        Button cl = new Button("Close", l -> dialog.close());
        dialog.setHeaderTitle("Registration successful!");
        dialog.getFooter().add(log, cl);

        add(title, name, name, email, phone, password, errorMessageField, btnGroup, emptySpace, submitButton, notification, dialog);

        submitButton.addClickListener(e -> {
            if (name.getValue().equals("") || email.getValue().equals("") || phone.getValue().equals("") || password.getValue().equals("")) {

                notification.open();

            } else {
                RegisterBL reg = new RegisterBL();
                if (reg.insertUser(name.getValue(), email.getValue(), phone.getValue(), password.getValue(), btnGroup.getValue()))
                    dialog.open();

            }
        });
        setMaxWidth("500px");

        setAlignItems(Alignment.CENTER);
    }

    public PasswordField getPasswordField() {
        return password;
    }

    public Span getErrorMessageField() {
        return errorMessageField;
    }

    public RadioButtonGroup getBtnGroup() {
        return btnGroup;
    }

    public Button getSubmitButton() {
        return submitButton;
    }

    private void setRequiredIndicatorVisible(HasValueAndElement<?, ?>... components) {
        Stream.of(components).forEach(comp -> comp.setRequiredIndicatorVisible(true));
    }

}