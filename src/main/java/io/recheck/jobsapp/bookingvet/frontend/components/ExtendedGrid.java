package io.recheck.jobsapp.bookingvet.frontend.components;

import com.vaadin.flow.component.grid.Grid;

import java.util.List;

public class ExtendedGrid<T> extends Grid<T> {

    private List<T> dataProvider;

    public ExtendedGrid(List<T> dataProvider) {
        this.dataProvider = dataProvider;
        setSelectionMode(SelectionMode.SINGLE);
        setItems(dataProvider);
    }

    public void addItem(T item) {
        dataProvider.add(item);
        refreshUI();
    }


    public void refreshUI() {
        getDataProvider().refreshAll();
    }

    public T getSelectedItem() {
        return getSelectedItems().iterator().next();
    }

}
