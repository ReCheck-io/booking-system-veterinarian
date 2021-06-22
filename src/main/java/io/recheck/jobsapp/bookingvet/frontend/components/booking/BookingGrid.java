package io.recheck.jobsapp.bookingvet.frontend.components.booking;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import io.recheck.jobsapp.bookingvet.backend.entity.Booking;
import io.recheck.jobsapp.bookingvet.frontend.components.ExtendedGrid;

import java.util.List;

@CssImport("./styles/shared-styles.css")
public class BookingGrid extends ExtendedGrid<Booking> {

    private BookingGridListeners bookingGridListeners;

    public BookingGrid(List<Booking> dataProvider, BookingGridListeners bookingGridListeners) {
        super(dataProvider);
        this.bookingGridListeners = bookingGridListeners;
        initColumns();
    }

    private void initColumns() {
        addColumn(Booking::getOwnerName).setHeader("First name");
        addColumn(Booking::getOwnerLastName).setHeader("Last name");
        addColumn(Booking::getAnimalSpecies).setHeader("Pet");
        addColumn(Booking::getVisitDateTime).setHeader("Visit date/time");
        addColumn(Booking::getVisitReason).setHeader("Visit reason");

        addComponentColumn(booking -> {
            Button button = new Button("Details");
            button.addClickListener(e -> {
                bookingGridListeners.detailsClickListener(booking);
            });
            return button;
        });

        addComponentColumn(booking -> {
            Button button = new Button("Close visit");
            button.addClickListener(e -> {
                bookingGridListeners.closeClickListeners(booking);
            });
            return button;
        });

        addComponentColumn(booking -> {
            Button button = new Button("Delete");
            button.addClickListener(e -> {
                bookingGridListeners.deleteClickListeners(booking);
            });
            return button;
        });


        getColumns().forEach(c -> c.setAutoWidth(true));
        setSelectionMode(Grid.SelectionMode.NONE);
    }

}
