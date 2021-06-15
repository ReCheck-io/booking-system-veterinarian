package io.recheck.jobsapp.bookingvet.backend.entity;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface BookingRepository  extends CrudRepository<Booking, Long> {

    List<Booking> findByOwnerNameAndPrincipalName(String ownerName, String principalName);
    List<Booking> findByPrincipalName(String principalName);
    Optional<Booking> findByIdAndPrincipalName(Long id, String principalName);
    void deleteByPrincipalName(String principalName);

}
