package io.recheck.jobsapp.bookingvet.backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class FindByOwnerDTO {

    @NotBlank(message = "ownerName may not be empty.")
    private String ownerName;

    @NotBlank(message = "ownerLastName may not be empty.")
    private String ownerLastName;

}
