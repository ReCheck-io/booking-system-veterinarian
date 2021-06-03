package io.recheck.jobsapp.bookingvet.restservice.controller;

import io.recheck.jobsapp.bookingvet.restservice.dto.BookingUpdateDTO;
import io.recheck.jobsapp.bookingvet.restservice.dto.FindByOwnerDTO;
import io.recheck.jobsapp.bookingvet.restservice.dto.VisitDateTimeClosedDTO;
import io.recheck.jobsapp.bookingvet.restservice.entity.Booking;
import io.recheck.jobsapp.bookingvet.restservice.entity.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class BookingController {

    @Autowired
    private BookingRepository bookingRepository;

    @GetMapping("/booking")
    public Iterable<Booking> getAll() {
        return bookingRepository.findAll();
    }

    @GetMapping("/booking/{id}")
    public Optional<Booking> getById(@PathVariable Long id) {
        return bookingRepository.findById(id);
    }

    @GetMapping("/bookings")
    public List<Booking> findByOwner(@Valid FindByOwnerDTO findByOwnerDTO) {
        return bookingRepository.findByOwnerName(findByOwnerDTO.getOwnerName());
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
