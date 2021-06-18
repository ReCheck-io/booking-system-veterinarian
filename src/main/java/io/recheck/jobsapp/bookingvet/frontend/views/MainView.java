package io.recheck.jobsapp.bookingvet.frontend.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;

@Route
@CssImport("./styles/shared-styles.css")
public class MainView extends AppLayout {

    public MainView() {
        addToNavbar(true, createHeaderContent());
    }

    private Component createHeaderContent() {
        H3 viewTitle = new H3("Online Appointment Booking for Veterinary");
        viewTitle.addClassName("viewTitle");

        Anchor logout = new Anchor("logout", "Log out");
        logout.setHeight("150%");

        HorizontalLayout header = new HorizontalLayout(viewTitle, logout);
        header.getThemeList().set("dark", true);
        header.setWidthFull();
        header.addClassName("header");
        header.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        return header;
    }

}
