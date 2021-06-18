package io.recheck.jobsapp.bookingvet.frontend.views;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.router.Route;

@Route(value = "booking", layout = MainView.class)
public class BookingView extends Div {

    public BookingView() {
        add(new Label("Booking........."));
    }
}
