package io.recheck.jobsapp.bookingvet.backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.recheck.jobsapp.bookingvet.backend.entity.Booking;
import io.recheck.jobsapp.bookingvet.utils.DateTimeUtils;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class BookingUpdateDTO {

    @NotNull
    private Long id;

    @NotBlank
    @Pattern(regexp = "[a-zA-Z]+")
    private String animalSpecies;

    @NotBlank
    @Pattern(regexp = "[a-zA-Z]+")
    private String animalBreed;

    @NotNull
    private Integer animalAge;

    @NotBlank
    @Pattern(regexp = "[a-zA-Z]+")
    private String ownerName;

    @NotBlank
    @Size(max=50,message="Last name size must be max 50 characters.")
    private String ownerLastName;

    @NotBlank
    private String visitReason;

    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime visitDateTime;

    @NotNull
    private boolean visitIsFirstTime;

    private boolean visitDateTimeClosed;

    public BookingUpdateDTO(Booking booking) {
        BeanUtils.copyProperties(booking, this);

        LocalDateTime localDateTime1 = DateTimeUtils.convertOrReturnNull(booking.getVisitDateTime());
        if (localDateTime1 != null) {
            setVisitDateTime(localDateTime1);
        }
    }

    public void setVisitDateTime(LocalDateTime visitDateTime) {
        this.visitDateTime = visitDateTime.minusHours(2);
    }
}
