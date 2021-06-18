package io.recheck.jobsapp.bookingvet.frontend.security;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import io.recheck.jobsapp.bookingvet.frontend.views.AdminView;
import io.recheck.jobsapp.bookingvet.frontend.views.BookingView;
import io.recheck.jobsapp.bookingvet.frontend.views.LoginView;
import org.springframework.stereotype.Component;

@Component
public class ConfigureUIServiceInitListener implements VaadinServiceInitListener {

    @Override
    public void serviceInit(ServiceInitEvent event) {
        event.getSource().addUIInitListener(uiEvent -> {
            final UI ui = uiEvent.getUI();
            ui.addBeforeEnterListener(this::authenticateNavigation);
        });
    }

    private void authenticateNavigation(BeforeEnterEvent event) {
        if (!LoginView.class.equals(event.getNavigationTarget()) && !SecurityUtils.isUserLoggedIn()) {
            event.rerouteTo(LoginView.class);
        }
        if (SecurityUtils.isUserLoggedIn()) {
            if (SecurityUtils.isAdminRole()) {
                event.rerouteTo(AdminView.class);
            }
            if (SecurityUtils.isUserRole()) {
                event.rerouteTo(BookingView.class);
            }
        }
    }
}
