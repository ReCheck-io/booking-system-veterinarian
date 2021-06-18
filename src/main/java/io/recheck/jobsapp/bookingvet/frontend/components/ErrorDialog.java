package io.recheck.jobsapp.bookingvet.frontend.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class ErrorDialog extends Dialog {

    private Button closeButton = new Button("Close");

    private VerticalLayout content = new VerticalLayout();

    public ErrorDialog() {
        initLayout();
        initListeners();
    }

    public void initListeners() {
        closeButton.addClickListener(listener -> close());
    }

    public void initLayout() {
        setCloseOnEsc(true);
        setCloseOnOutsideClick(true);
        add(content);
        add(closeButton);
    }

    public void open(String internalError) {
        content.removeAll();
        content.add(new Label(internalError));
        open();
    }

}
