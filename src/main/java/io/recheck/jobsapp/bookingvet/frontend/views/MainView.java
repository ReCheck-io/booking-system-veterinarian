package io.recheck.jobsapp.bookingvet.frontend.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;
import io.recheck.jobsapp.bookingvet.frontend.security.SecurityUtils;
import org.springframework.security.core.userdetails.User;

@Route
@CssImport("./styles/shared-styles.css")
public class MainView extends AppLayout {

    private User user = (User) SecurityUtils.getUserAuth().getPrincipal();

    public MainView() {
        addToNavbar(true, createHeaderContent());
    }

    private Component createHeaderContent() {
        H3 viewTitle = new H3("Online Appointment Booking for Veterinary");
        viewTitle.addClassName("viewTitle");

        Anchor logout = new Anchor("logout", "Log out");
        logout.setHeight("150%");
        if (SecurityUtils.isUserRole())
            logout.setVisible(false);

        HorizontalLayout header = new HorizontalLayout(viewTitle, logout);
        header.getThemeList().set("dark", true);
        header.setWidthFull();
        header.addClassName("header");
        header.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        return header;
    }

}
