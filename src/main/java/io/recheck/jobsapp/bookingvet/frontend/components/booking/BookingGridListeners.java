package io.recheck.jobsapp.bookingvet.frontend.components.booking;

import io.recheck.jobsapp.bookingvet.backend.entity.Booking;

public interface BookingGridListeners {

    void detailsClickListener(Booking booking);
    void deleteClickListeners(Booking booking);
    void closeClickListeners(Booking booking);


}
