package io.recheck.jobsapp.bookingvet.backend.controller;

import io.recheck.jobsapp.bookingvet.backend.dto.BookingUpdateDTO;
import io.recheck.jobsapp.bookingvet.backend.dto.FindByOwnerDTO;
import io.recheck.jobsapp.bookingvet.backend.dto.VisitDateTimeClosedDTO;
import io.recheck.jobsapp.bookingvet.backend.entity.Booking;
import io.recheck.jobsapp.bookingvet.backend.service.BookingService;
import io.recheck.jobsapp.bookingvet.frontend.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import java.util.Optional;

@RestController
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping("/booking")
    public Iterable<Booking> get(FindByOwnerDTO findByOwnerDTO) {
        return bookingService.get(findByOwnerDTO, (User) SecurityUtils.getUserAuth().getPrincipal());
    }

    @GetMapping("/booking/{id}")
    public Optional<Booking> getById(@PathVariable Long id) {
        return bookingService.getById(id, (User) SecurityUtils.getUserAuth().getPrincipal());
    }

    @PostMapping("/booking")
    public Booking save(@RequestBody @Valid Booking booking) {
        return bookingService.save(booking, (User) SecurityUtils.getUserAuth().getPrincipal());
    }

    @PutMapping("/booking")
    public void update(@RequestBody @Valid BookingUpdateDTO bookingUpdateDTO) {
        bookingService.update(bookingUpdateDTO, (User) SecurityUtils.getUserAuth().getPrincipal());
    }

    @DeleteMapping("/booking/{id}")
    public void deleteById(@PathVariable Long id) {
        bookingService.deleteById(id);
    }

    @PutMapping("/booking/{id}/visitDateTimeClosed")
    public void updateVisitDateTimeClosed(@PathVariable Long id, @RequestBody VisitDateTimeClosedDTO visitDateTimeClosedDTO) {
        bookingService.updateVisitDateTimeClosed(id, visitDateTimeClosedDTO, (User) SecurityUtils.getUserAuth().getPrincipal());
    }

}
