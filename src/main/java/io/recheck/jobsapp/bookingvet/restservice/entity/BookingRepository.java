package io.recheck.jobsapp.bookingvet.restservice.entity;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookingRepository  extends CrudRepository<Booking, Long> {

    List<Booking> findByOwnerName(String ownerName);

}
