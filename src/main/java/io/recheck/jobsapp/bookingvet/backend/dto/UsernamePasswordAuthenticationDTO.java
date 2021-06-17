package io.recheck.jobsapp.bookingvet.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class UsernamePasswordAuthenticationDTO {

    private String name;

    private List<String> authorities;

    public UsernamePasswordAuthenticationDTO(Authentication authentication) {
        this.name = authentication.getName();
        this.authorities = new ArrayList<>();
        authentication.getAuthorities().forEach(e -> authorities.add(e.getAuthority()));
    }

}

