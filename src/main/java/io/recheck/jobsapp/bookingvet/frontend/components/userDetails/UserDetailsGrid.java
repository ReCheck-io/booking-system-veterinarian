package io.recheck.jobsapp.bookingvet.frontend.components.userDetails;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import io.recheck.jobsapp.bookingvet.backend.dto.UserDetailsDTO;
import io.recheck.jobsapp.bookingvet.frontend.components.ExtendedGrid;
import lombok.Getter;

import java.util.List;

@Getter
public class UserDetailsGrid extends ExtendedGrid<UserDetailsDTO> {

    private UserDetailsGridListeners userDetailsGridListeners;

    public UserDetailsGrid(List<UserDetailsDTO> dataProvider, UserDetailsGridListeners userDetailsGridListeners) {
        super(dataProvider);
        this.userDetailsGridListeners = userDetailsGridListeners;
        initColumns();
    }

    private void initColumns() {
        addColumn(UserDetailsDTO::getUsername);
        addColumn(UserDetailsDTO::getPassword);
        addColumn(UserDetailsDTO::isEnabled);

        addComponentColumn(userDetails -> {
            Button button;
            if (userDetails.isEnabled()) {
                button = new Button("Disable");
            }
            else {
                button = new Button("Enable");
            }

            button.addClickListener(e -> {
                userDetailsGridListeners.disableClickListener(userDetails);
            });

            return button;
        });

        addComponentColumn(userDetails -> {
            Button button = new Button("Delete");
            button.addClickListener(e -> {
                userDetailsGridListeners.deleteClickListener(userDetails);
            });
            return button;
        });

        getColumns().forEach(c -> c.setAutoWidth(true));
        setSelectionMode(Grid.SelectionMode.NONE);
    }

}
