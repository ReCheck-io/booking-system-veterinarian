package io.recheck.jobsapp.bookingvet.backend.controller;

import io.recheck.jobsapp.bookingvet.backend.dto.BookingUpdateDTO;
import io.recheck.jobsapp.bookingvet.backend.dto.FindByOwnerDTO;
import io.recheck.jobsapp.bookingvet.backend.dto.VisitDateTimeClosedDTO;
import io.recheck.jobsapp.bookingvet.backend.entity.Booking;
import io.recheck.jobsapp.bookingvet.backend.entity.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import java.util.Comparator;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@RestController
public class BookingController {

    @Autowired
    private BookingRepository bookingRepository;

    @GetMapping("/booking")
    public Iterable<Booking> get(FindByOwnerDTO findByOwnerDTO) {
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
            return bookingRepository.findByOwnerName(findByOwnerDTO.getOwnerName());
        }
        return bookingRepository.findAll();
    }

    @GetMapping("/booking/{id}")
    public Optional<Booking> getById(@PathVariable Long id) {
        return bookingRepository.findById(id);
    }

    @PostMapping("/booking")
    public Booking save(@RequestBody @Valid Booking booking) {
        return bookingRepository.save(booking);
    }

    @PutMapping("/booking")
    public void update(@RequestBody @Valid BookingUpdateDTO bookingUpdateDTO) {
        bookingRepository.save(new Booking(bookingUpdateDTO));
    }

    @DeleteMapping("/booking/{id}")
    public void delete(@PathVariable Long id) {
        bookingRepository.deleteById(id);
    }

    @PutMapping("/booking/{id}/visitDateTimeClosed")
    public void updateVisitDateTimeClosed(@PathVariable Long id, @RequestBody VisitDateTimeClosedDTO visitDateTimeClosedDTO) {
        Optional<Booking> byId = bookingRepository.findById(id);
        byId.ifPresent(b -> {
            b.setVisitDateTimeClosed(visitDateTimeClosedDTO.isValue());
            bookingRepository.save(b);
        });
    }

}
