package io.recheck.jobsapp.bookingvet.backend.service;

import io.recheck.jobsapp.bookingvet.backend.dto.BookingUpdateDTO;
import io.recheck.jobsapp.bookingvet.backend.dto.FindByOwnerDTO;
import io.recheck.jobsapp.bookingvet.backend.dto.VisitDateTimeClosedDTO;
import io.recheck.jobsapp.bookingvet.backend.entity.Booking;
import io.recheck.jobsapp.bookingvet.backend.entity.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.*;
import java.util.Comparator;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    public Iterable<Booking> get(User user) {
        return bookingRepository.findByPrincipalName(user.getUsername());
    }

    public Iterable<Booking> get(FindByOwnerDTO findByOwnerDTO, User user) {
        if (StringUtils.hasText(findByOwnerDTO.getOwnerName())) {
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<FindByOwnerDTO>> constraintViolations = validator.validate(findByOwnerDTO);
            if (!constraintViolations.isEmpty()) {
                Comparator<ConstraintViolation<FindByOwnerDTO>> byTimestamp =
                        Comparator.comparing(ConstraintViolation::getMessage);

                Supplier<TreeSet<ConstraintViolation<FindByOwnerDTO>>> supplier =
                        () -> new TreeSet<>(byTimestamp);

                constraintViolations = constraintViolations.stream()
                        .collect(Collectors.toCollection(supplier));

                throw new javax.validation.ConstraintViolationException(constraintViolations);
            }
            return bookingRepository.findByOwnerNameAndPrincipalName(findByOwnerDTO.getOwnerName(), user.getUsername());
        }
        return get(user);
    }

    public Optional<Booking> getById(@PathVariable Long id, User user) {
        return bookingRepository.findByIdAndPrincipalName(id, user.getUsername());
    }

    public Booking save(@RequestBody @Valid Booking booking, User user) {
        booking.setPrincipalName(user.getUsername());
        return bookingRepository.save(booking);
    }

    public void update(@RequestBody @Valid BookingUpdateDTO bookingUpdateDTO, User user) {
        Optional<Booking> storedBooking = bookingRepository.findByIdAndPrincipalName(bookingUpdateDTO.getId(), user.getUsername());
        if (storedBooking.isPresent()) {
            Booking updatedBooking = new Booking(bookingUpdateDTO);
            updatedBooking.setPrincipalName(user.getUsername());
            bookingRepository.save(updatedBooking);
        }
    }

    public void deleteById(@PathVariable Long id) {
        bookingRepository.deleteById(id);
    }

    public void updateVisitDateTimeClosed(@PathVariable Long id, @RequestBody VisitDateTimeClosedDTO visitDateTimeClosedDTO, User user) {
        Optional<Booking> booking = bookingRepository.findByIdAndPrincipalName(id, user.getUsername());
        booking.ifPresent(b -> {
            b.setVisitDateTimeClosed(visitDateTimeClosedDTO.isValue());
            bookingRepository.save(b);
        });
    }

}
