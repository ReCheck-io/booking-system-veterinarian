package io.recheck.jobsapp.bookingvet.frontend.views;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.recheck.jobsapp.bookingvet.frontend.components.LoginStyledForm;

@Route("login")
@PageTitle("Login | Booking for Veterinary")
public class LoginView extends HorizontalLayout implements BeforeEnterObserver {

	private LoginStyledForm login = new LoginStyledForm();
	private VerticalLayout loginLayout = new VerticalLayout();
	private Div divLayout = new Div();

	public LoginView(){
		setSizeFull();
		setAlignItems(Alignment.CENTER);
		setJustifyContentMode(JustifyContentMode.CENTER);

		login.setAction("login");
		login.setForgotPasswordButtonVisible(false);


		loginLayout.add(new H1("Booking for Veterinary"), login);

		Image image = new Image();
		image.setSrc("https://images.unsplash.com/photo-1583337130417-3346a1be7dee?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=700&q=80");
		image.addClassName("loginImage");
		divLayout.add(image);

		add(divLayout, loginLayout);

		applyCss();
	}

	private void applyCss() {
		addClassName("login-view");

		login.addClassName("login-form");

		divLayout.getStyle().clear();
		divLayout.addClassName("loginLeftColumnLayout");

		loginLayout.getStyle().clear();
		loginLayout.addClassName("loginRightColumnLayout");
	}

	@Override
	public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
		// inform the user about an authentication error
		if(beforeEnterEvent.getLocation()
				.getQueryParameters()
				.getParameters()
				.containsKey("error")) {
			login.setError(true);
		}
	}
}