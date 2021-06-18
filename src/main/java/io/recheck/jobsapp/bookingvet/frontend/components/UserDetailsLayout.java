package io.recheck.jobsapp.bookingvet.frontend.components;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import io.recheck.jobsapp.bookingvet.backend.dto.UserDetailsDTO;

public class UserDetailsLayout extends Div {

    private H3 title = new H3();

    private TextField firstnameField = new TextField();
    private TextField lastnameField = new TextField();

    private Button createButton = new Button("Create");
    private Button cancelButton = new Button("Cancel");

    public UserDetailsLayout() {
        initLayout();
        initComponents();
    }

    private void initComponents() {
        title.setText("Create new");
        firstnameField.setPlaceholder("enter first name");
        lastnameField.setPlaceholder("enter last name");
    }

    public void clearData() {
        firstnameField.setValue("");
        lastnameField.setValue("");
    }

    public UserDetailsDTO getData() {
        return new UserDetailsDTO(firstnameField.getValue(), lastnameField.getValue());
    }

    public void createClickListener(ComponentEventListener<ClickEvent<Button>> listener) {
        createButton.addClickListener(listener);
    }

    public void cancelClickListener(ComponentEventListener<ClickEvent<Button>> listener) {
        cancelButton.addClickListener(listener);
    }

    public void initLayout() {
        FormLayout formLayout = new FormLayout();
        formLayout.add(firstnameField, lastnameField);

        HorizontalLayout buttonsLayout = new HorizontalLayout();
        buttonsLayout.add(createButton, cancelButton);

        add(title, formLayout, buttonsLayout);

        setVisible(false);
    }

    public void toCreateState() {
        clearData();
        setVisible(true);
    }

}
