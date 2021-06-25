package io.recheck.jobsapp.bookingvet.frontend.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import io.recheck.jobsapp.bookingvet.backend.dto.BookingUpdateDTO;
import io.recheck.jobsapp.bookingvet.backend.dto.VisitDateTimeClosedDTO;
import io.recheck.jobsapp.bookingvet.backend.entity.Booking;
import io.recheck.jobsapp.bookingvet.backend.service.BookingService;
import io.recheck.jobsapp.bookingvet.frontend.components.ErrorDialog;
import io.recheck.jobsapp.bookingvet.frontend.components.booking.BookingFormLayout;
import io.recheck.jobsapp.bookingvet.frontend.components.booking.BookingGrid;
import io.recheck.jobsapp.bookingvet.frontend.components.booking.BookingGridListeners;
import io.recheck.jobsapp.bookingvet.frontend.security.SecurityUtils;
import io.recheck.jobsapp.bookingvet.utils.DateTimeUtils;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Route(value = "booking", layout = MainView.class)
public class BookingView extends Div {

    private BookingService bookingService;
    private User user = (User) SecurityUtils.getUserAuth().getPrincipal();
    private List<Booking> bookings;

    private Button newButton = new Button("New Visitation");

    private BookingGrid bookingGrid;
    private BookingGridListeners bookingGridListeners;

    private VerticalLayout gridLayout;

    private BookingFormLayout bookingFormLayout = new BookingFormLayout();

    private ErrorDialog errorDialog = new ErrorDialog();

    private VerticalLayout viewLayout = new VerticalLayout();

    public BookingView(BookingService bookingService) {
        this.bookingService = bookingService;
        bookings = Lists.newArrayList(bookingService.get(user).iterator());
        initListeners();
        initLayout();
    }

    private void initLayout() {
        bookingGrid = new BookingGrid(bookings, bookingGridListeners);

        HorizontalLayout horizontalLayout = new HorizontalLayout(new Label("Existing visitations"), newButton);
        horizontalLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        gridLayout = new VerticalLayout(horizontalLayout, bookingGrid);

        viewLayout.add(bookingFormLayout);

        add(new HorizontalLayout(gridLayout, viewLayout));

        applyCss();
    }

    private void applyCss() {
        addClassName("panel-view");

        bookingGrid.addClassName("table-1");

        gridLayout.getStyle().clear();
        gridLayout.addClassName("leftColumnLayout");

        viewLayout.getStyle().clear();
        viewLayout.addClassName("rightColumnLayout");
    }

    private void initListeners() {
        newButton.addClickListener(e -> {
            toInitState();
            bookingFormLayout.toCreateState();
        });

        bookingGridListeners = new BookingGridListeners() {
            @Override
            public void detailsClickListener(Booking booking) {
                try {
                    toInitState();
                    bookingFormLayout.toUpdateState(booking);
                } catch (Exception ex) {
                    errorDialog.open(ex.getMessage());
                }
                bookingGrid.refreshUI();
            }

            @Override
            public void deleteClickListeners(Booking booking) {
                bookingService.deleteById(booking.getId());
                bookings.remove(booking);
                bookingGrid.refreshUI();
            }

            @Override
            public void closeClickListeners(Booking booking) {
                bookingService.updateVisitDateTimeClosed(booking.getId(), new VisitDateTimeClosedDTO(true), user);
                booking.setVisitDateTimeClosed(true);
                bookingGrid.refreshUI();
            }
        };

        bookingFormLayout.createClickListener(e -> {
            try {
                Booking bookingForm = bookingFormLayout.getData();
                if (bookingForm.isVisitIsFirstTime() && StringUtils.hasText(bookingForm.getVisitDateTime())) {
                    LocalDateTime localDateTime = DateTimeUtils.convertOrReturnNull(bookingForm.getVisitDateTime());
                    DateTimeUtils.validateIsBeforeOrIsEqual(localDateTime);
                }
                if (bookingForm.getVisitReason().equals("Emergence")) {
                    bookingForm.setAnimalAge(null);
                }
                validateCreate(bookingForm);
                Booking savedBooking = bookingService.save(bookingForm, user);
                bookingGrid.addItem(savedBooking);
                toInitState();
            } catch (Exception ex) {
                ex.printStackTrace();
                errorDialog.open(ex.toString());
            }
        });

        bookingFormLayout.updateClickListener(e -> {
            try {
                Booking bookingForm = bookingFormLayout.getData();
                BookingUpdateDTO bookingUpdateDTO = new BookingUpdateDTO(bookingForm);
                validateUpdate(bookingUpdateDTO);
                bookingService.update(bookingUpdateDTO, user);
                bookingGrid.refreshUI();
                toInitState();
            } catch (Exception ex) {
                ex.printStackTrace();
                errorDialog.open(ex.toString());
            }
        });

        bookingFormLayout.cancelClickListener(e -> toInitState());
    }

    private void toInitState() {
        bookingFormLayout.setVisible(false);
    }

    private void validateCreate(Booking bookingForm) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Booking>> constraintViolations = validator.validate(bookingForm);
        if (!constraintViolations.isEmpty()) {
            throw new javax.validation.ConstraintViolationException(constraintViolations);
        }
    }

    private void validateUpdate(BookingUpdateDTO bookingForm) throws Exception {
        DateTimeUtils.validateIsBeforeOrIsEqual(bookingForm.getVisitDateTime());

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<BookingUpdateDTO>> constraintViolations = validator.validate(bookingForm);
        if (!constraintViolations.isEmpty()) {
            throw new javax.validation.ConstraintViolationException(constraintViolations);
        }
    }
}
