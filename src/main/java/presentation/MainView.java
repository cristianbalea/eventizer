package presentation;

import businesslogic.EventBL;
import businesslogic.ProfileBL;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import static com.vaadin.flow.component.button.ButtonVariant.LUMO_TERTIARY_INLINE;

@Route("user/:userID/")
@PageTitle("Home")
public class MainView extends AppLayout implements Observer, BeforeEnterObserver {
    Dialog dialog = new Dialog();
    Dialog eventDialog = new Dialog();
    Notification notification = new Notification();
    private VerticalLayout main = new VerticalLayout();
    private EventBL eventBL = new EventBL();
    private String userID;
    private String userEmail;
    private String userRole;
    private ProfileBL profileLogic = new ProfileBL();
    private Button modify = new Button("Modify account info");
    private Button myEvents = new Button("My events");
    private Label upcoming = new Label("Upcoming events:");
    private Button organizeNow = new Button();

    public MainView() {
        getStyle().set("background", "url(https://iili.io/HcFwSLv.png)");
        setContent(main);
    }

    @Override
    public void update(Observable o, Object arg) {
        Notification notification = createNotification();
        notification.open();
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        userID = event.getRouteParameters().get("userID").orElse("haha");
        userEmail = profileLogic.getEmailAddress(Integer.parseInt(userID));
        userRole = profileLogic.getRole(Integer.parseInt(userID));
        configureHeader(userID);
        configureEvents(userID);
        //configureOrganize(userID);
    }

    private void configureHeader(String user) {
        HorizontalLayout head = new HorizontalLayout();
        H3 hello = new H3("Hi, " + profileLogic.getUsername(user) + "!");
        hello.getStyle().set("font-family", "Georgia");
        hello.getStyle().set("font-size", "48px");

        upcoming.getStyle().set("font-family", "Georgia");
        upcoming.getStyle().set("font-size", "32px");

        HorizontalLayout buttons = new HorizontalLayout();

        modify.getStyle().set("background-color", "#EF6C33");
        modify.getStyle().set("color", "#E1DDDB");
        modify.getStyle().set("font-size", "20px");
        modify.getStyle().set("padding", "20px 40px");
        modify.getStyle().set("border-radius", "0px");

        Button cl = new Button("Close", l -> dialog.close());

        dialog.setHeaderTitle("Modify account details");
        dialog.add(new UserModifyView(user));
        dialog.getFooter().add(cl);

        modify.addClickListener(l -> {
            dialog.open();
        });

        myEvents.getStyle().set("background-color", "#EF6C33");
        myEvents.getStyle().set("color", "#E1DDDB");
        myEvents.getStyle().set("font-size", "20px");
        myEvents.getStyle().set("padding", "20px 40px");
        myEvents.getStyle().set("border-radius", "0px");


        VerticalLayout scrollable = new VerticalLayout();

        Scroller scroll = new Scroller();
        scroll.setScrollDirection(Scroller.ScrollDirection.HORIZONTAL);
        scroll.setHeight("500px");
        scroll.setWidthFull();

        ArrayList<EventView> evs = new ArrayList<>();

        HorizontalLayout ev = new HorizontalLayout();
        ArrayList<Integer> events = profileLogic.getEvents(user);
        if (events != null && events.size() > 0) {
            for (Integer e : events) {
                EventView eventView = new EventView(e, userID, true);
                evs.add(eventView);
            }
        }

        for(EventView e : evs) {
            ev.add(e);
        }

        ev.setPadding(true);
        ev.getStyle().set("display", "inline-flex");

        scroll.setContent(ev);

        scrollable.add(scroll);

        Button cl2 = new Button("Close", l -> eventDialog.close());

        myEvents.addClickListener(l -> {
            eventDialog.open();
        });

        eventDialog.setHeaderTitle("Events you are participating to");
        eventDialog.add(scrollable);
        eventDialog.getFooter().add(cl2);
        buttons.add(modify, myEvents);

        head.add(hello, buttons);
        head.setWidthFull();
        head.setAlignItems(FlexComponent.Alignment.CENTER);


        main.add(head);
        main.add(upcoming);

        head.setJustifyContentMode(FlexComponent.JustifyContentMode.AROUND);
    }

    private void configureEvents(String userID) {
        VerticalLayout scrollable = new VerticalLayout();

        Scroller scroll = new Scroller();
        scroll.setScrollDirection(Scroller.ScrollDirection.HORIZONTAL);
        scroll.setHeight("500px");
        scroll.setWidthFull();

        ArrayList<EventView> evs = new ArrayList<>();

        HorizontalLayout ev = new HorizontalLayout();
        ArrayList<Integer> events = eventBL.getEvents();
        if (events != null && events.size() > 0) {
            for (Integer e : events) {
                EventView eventView = new EventView(e, userID, false);
                evs.add(eventView);
            }
        }

        for(EventView e : evs) {
            ev.add(e);
        }

        ev.setPadding(true);
        ev.getStyle().set("display", "inline-flex");

        scroll.setContent(ev);

        scrollable.add(scroll);
        main.add(scrollable);

    }

    private void configureOrganize(String user) {
        HorizontalLayout org = new HorizontalLayout();

        organizeNow.setText("Organize your own event now!");

        organizeNow.getStyle().set("background-color", "#EF6C33");
        organizeNow.getStyle().set("color", "#E1DDDB");
        organizeNow.getStyle().set("font-size", "20px");
        organizeNow.getStyle().set("padding", "20px 40px");
        organizeNow.getStyle().set("border-radius", "0px");

        org.setWidthFull();
        org.setHeightFull();
        org.add(organizeNow);

        org.setAlignItems(FlexComponent.Alignment.END);
        org.setJustifyContentMode(FlexComponent.JustifyContentMode.START);
        main.add(org);
    }

    private static Notification createNotification() {
        Notification notification = new Notification();

        Span name = new Span();
        name.getStyle().set("font-weight", "500");
        Div info = new Div(
                name,
                new Text("Log in succeeded!")
        );

        HorizontalLayout layout = new HorizontalLayout(info, createCloseButton(notification));
        layout.setAlignItems(FlexComponent.Alignment.CENTER);

        notification.add(layout);

        return notification;
    }

    private static Button createCloseButton(Notification notification) {
        Button closeButton = new Button(
                VaadinIcon.CLOSE_SMALL.create(),
                clickEvent -> notification.close());
        closeButton.addThemeVariants(LUMO_TERTIARY_INLINE);

        return closeButton;
    }
}

