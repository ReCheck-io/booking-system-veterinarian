package io.recheck.jobsapp.bookingvet.backend.entity;

import io.recheck.jobsapp.bookingvet.backend.dto.BookingUpdateDTO;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String animalSpecies;

    @Pattern(regexp = "[a-zA-Z\\s\\u002D\\u002E]+")
    private String animalBreed;

    private Integer animalAge = 0;

    private String ownerName;

    @Size(max=50,message="Last name size must be max 50 characters.")
    private String ownerLastName;

    private String visitReason = "Diagnosing";

    private String visitDateTime;

    private boolean visitIsFirstTime = true;

    private boolean visitDateTimeClosed;

    public Booking(BookingUpdateDTO bookingUpdateDTO) {
        BeanUtils.copyProperties(bookingUpdateDTO, this);
        String datePattern = "yyyy-MM-dd HH:mm";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(datePattern);
        this.setVisitDateTime(bookingUpdateDTO.getVisitDateTime().minusHours(2).format(dateTimeFormatter));
    }
}
