package io.recheck.jobsapp.bookingvet.frontend.views;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.router.Route;

@Route(value = "admin", layout = MainView.class)
public class AdminView extends Div {

    public AdminView() {
        add(new Label("Admin........."));
    }

}
