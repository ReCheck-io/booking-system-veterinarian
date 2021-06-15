package io.recheck.jobsapp.bookingvet.backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDetailsDTO {

    private String firstName;
    private String lastName;

    private String username;
    private String password;

    private boolean enabled;

}
