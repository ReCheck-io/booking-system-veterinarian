package io.recheck.jobsapp.bookingvet.backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class UserDetailsDTO {

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    private String username;
    private String password;

    @NotNull
    private boolean enabled;

    public UserDetailsDTO(String firstname1, String lastname1) {
        this.firstName = firstname1;
        this.lastName = lastname1;
        this.enabled = true;
    }

    public UserDetailsDTO(UserDetails userDetails) {
        this.username = userDetails.getUsername();
        this.enabled = userDetails.isEnabled();
    }

    public boolean equals(Object o){
        if(o == null)
            return false;
        if(!(o instanceof UserDetailsDTO))
            return false;

        UserDetailsDTO other = (UserDetailsDTO) o;
        return this.username.equals(other.username);
    }
}
