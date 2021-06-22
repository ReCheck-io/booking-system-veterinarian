package io.recheck.jobsapp.bookingvet.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.recheck.jobsapp.bookingvet.backend.dto.BookingUpdateDTO;
import io.recheck.jobsapp.bookingvet.utils.DateTimeUtils;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

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

    @JsonIgnore
    private String principalName;

    public Booking(BookingUpdateDTO bookingUpdateDTO) {
        BeanUtils.copyProperties(bookingUpdateDTO, this);
        this.setVisitDateTime(DateTimeUtils.convertOrReturnNull(bookingUpdateDTO.getVisitDateTime()));
    }

    public boolean equals(Object o) {
        if(o == null)
            return false;

        if(!(o instanceof Booking))
            return false;

        Booking other = (Booking) o;
        return this.getId().equals(other.id);
    }


    public void setAnimalAge(Integer animalAge) {
        this.animalAge = Optional.ofNullable(animalAge).orElse(0);
    }

    public void setVisitReason(String visitReason) {
        this.visitReason = Optional.ofNullable(visitReason).orElse("Diagnosing");
    }

    public void setVisitIsFirstTime(Boolean visitIsFirstTime) {
        this.visitIsFirstTime = Optional.ofNullable(visitIsFirstTime).orElse(true);
    }
}
