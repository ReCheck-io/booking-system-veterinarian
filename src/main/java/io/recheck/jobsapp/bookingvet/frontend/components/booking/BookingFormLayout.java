package io.recheck.jobsapp.bookingvet.frontend.components.booking;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;
import io.recheck.jobsapp.bookingvet.backend.entity.Booking;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BookingFormLayout extends Div {

    private enum PET {
        Dog, Cat, Bird, Other
    }

    private enum VisitReason {
        MED_CHECK("Medical check"),
        VACCINATION("Vaccination"),
        EMERGENCE("Emergence");

        private String value;
        VisitReason(String value1) {
        this.value = value1;
        }

        public String getValue() {
            return value;
        }
    }

    private RadioButtonGroup<PET> animalRadio = new RadioButtonGroup<>();
    private TextField animalOtherField = new TextField("Other:");
    private HorizontalLayout animalChooseLayout = new HorizontalLayout();

    private Booking currentBooking = null;
    private TextField animalBreedField = new TextField("", "Enter breed");
    private TextField animalAgeField = new TextField("", "Enter age");
    private TextField ownerNameField = new TextField("", "Enter owner first name");
    private TextField ownerLastNameField = new TextField("", "Enter owner last name");
    private RadioButtonGroup<String> visitReasonRadio = new RadioButtonGroup();
    private TextField visitDateTimeField = new TextField("", "Enter date & time (yyyy-MM-dd HH:mm)");
    private Checkbox visitIsFirstTimeCheckbox = new Checkbox("Is it for first time");

    private H3 title = new H3();
    private Button createButton = new Button("Create");
    private Button updateButton = new Button("Update");
    private Button cancelButton = new Button("Cancel");

    public BookingFormLayout() {
        initLayout();
        initComponents();
    }

    private void initComponents() {
        animalRadio.setLabel("Animal:");
        animalRadio.setItems(PET.values());
        animalRadio.setValue(PET.Dog);

        animalOtherField.setVisible(false);
        animalRadio.addValueChangeListener(e -> animalOtherField.setVisible(PET.Other.equals(e.getValue())));
        animalChooseLayout.add(animalRadio, animalOtherField);

        visitReasonRadio.setLabel("Reason: ");

        List<String> visitReasonList = Arrays.asList(VisitReason.values()).stream().map(v -> v.getValue()).collect(Collectors.toList());
        String[] visitReasonArr = visitReasonList.toArray(new String[visitReasonList.size()]);
        visitReasonRadio.setItems(visitReasonArr);

        applyCss();
    }

    private void applyCss() {
        addClassName("bookingFormLayout");
        animalBreedField.addClassName("width50");
        animalAgeField.addClassName("width50");
        ownerNameField.addClassName("width50");
        ownerLastNameField.addClassName("width50");
        visitDateTimeField.addClassName("width50");
    }

    public void setData(Booking booking) {
        currentBooking = booking;

        PET pet = null;
        if (booking.getAnimalSpecies() != null) {
            try {
                pet = PET.valueOf(booking.getAnimalSpecies());
            }
            catch (Exception e) {
                pet = PET.Other;
                animalOtherField.setVisible(true);
                animalOtherField.setValue(booking.getAnimalSpecies());
            }
            animalRadio.setValue(pet);
        }

        if (StringUtils.hasText(booking.getAnimalBreed())) {
            animalBreedField.setValue(booking.getAnimalBreed());
        }

        if (booking.getAnimalAge() != null) {
            animalAgeField.setValue(booking.getAnimalAge().toString());
        }

        if (pet != PET.Bird) {
            if (StringUtils.hasText(booking.getOwnerName())) {
                ownerNameField.setValue(booking.getOwnerName());
            }

            if (StringUtils.hasText(booking.getOwnerLastName())) {
                ownerLastNameField.setValue(booking.getOwnerLastName());
            }
        }


        try {
            visitReasonRadio.setValue(booking.getVisitReason());
        } catch (Exception e) { }

        if (StringUtils.hasText(booking.getVisitDateTime()))
            visitDateTimeField.setValue(booking.getVisitDateTime());

        visitIsFirstTimeCheckbox.setValue(booking.isVisitIsFirstTime());

        if (booking.isVisitDateTimeClosed()) {
            visitDateTimeField.clear();
        }



    }

    public void clearData() {
        currentBooking = null;

        animalRadio.setValue(PET.Dog);
        animalOtherField.setVisible(false);
        animalOtherField.clear();

        animalBreedField.clear();
        animalAgeField.clear();
        ownerNameField.clear();
        ownerLastNameField.clear();
        visitDateTimeField.clear();

        visitReasonRadio.clear();
        visitIsFirstTimeCheckbox.clear();

    }

    public Booking getData() {
        if (currentBooking == null) {
            currentBooking = new Booking();
        }

        if (animalRadio.getValue() != PET.Other) {
            currentBooking.setAnimalSpecies(animalRadio.getValue().name());
        }
        else {
            currentBooking.setAnimalSpecies(getStringOrNull(animalOtherField.getValue()));
        }


        currentBooking.setAnimalBreed(getStringOrNull(animalBreedField.getValue()));

        currentBooking.setAnimalAge(getIntOrNull(animalAgeField.getValue()));

        currentBooking.setOwnerName(getStringOrNull(ownerNameField.getValue()));

        currentBooking.setOwnerLastName(getStringOrNull(ownerLastNameField.getValue()));

        currentBooking.setVisitReason(getStringOrNull(visitReasonRadio.getValue()));

        currentBooking.setVisitDateTime(getStringOrNull(visitDateTimeField.getValue()));

        currentBooking.setVisitIsFirstTime(visitIsFirstTimeCheckbox.getValue());

        return currentBooking;
    }

    public void createClickListener(ComponentEventListener<ClickEvent<Button>> listener) {
        createButton.addClickListener(listener);
    }

    public void cancelClickListener(ComponentEventListener<ClickEvent<Button>> listener) {
        cancelButton.addClickListener(listener);
    }

    public void updateClickListener(ComponentEventListener<ClickEvent<Button>> listener) {
        updateButton.addClickListener(listener);
    }

    public void initLayout() {
        VerticalLayout formLayout = new VerticalLayout();
        formLayout.add(animalChooseLayout);
        formLayout.add(animalBreedField, animalAgeField, ownerNameField, ownerLastNameField);
        formLayout.add(visitDateTimeField, visitReasonRadio, visitIsFirstTimeCheckbox);

        HorizontalLayout buttonsLayout = new HorizontalLayout();
        buttonsLayout.add(createButton, updateButton, cancelButton);

        add(title, formLayout, buttonsLayout);

        setVisible(false);
    }

    public void toCreateState() {
        clearData();

        title.setText("Create new");

        createButton.setVisible(true);
        updateButton.setVisible(false);

        setVisible(true);
    }

    public void toUpdateState(Booking booking) {
        clearData();
        setData(booking);

        title.setText("Update");

        updateButton.setVisible(true);
        createButton.setVisible(false);

        setVisible(true);
    }

    private String getStringOrNull(String s) {
        if (!StringUtils.hasText(s)) {
            return null;
        }
        return s;
    }

    private Integer getIntOrNull(String s) {
        if (!StringUtils.hasText(s)) {
            return null;
        }
        return Integer.valueOf(s);
    }

}
