package io.recheck.jobsapp.bookingvet.backend.controller;

import io.recheck.jobsapp.bookingvet.backend.dto.UserDetailsDTO;
import io.recheck.jobsapp.bookingvet.backend.entity.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@RestController()
public class AdminController {

    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private BookingRepository bookingRepository;

    @PostMapping("/admin/user")
    public UserDetailsDTO createUser(@RequestBody UserDetailsDTO userDetailsDTO) {
        JdbcUserDetailsManager userDetailsManager = (JdbcUserDetailsManager) authenticationManagerBuilder.getDefaultUserDetailsService();

        String rndPassword = rndPassword();
        String genRndUserName = generateUsername(userDetailsDTO);
        User user = new User(genRndUserName, passwordEncoder.encode(rndPassword), getGrantedAuthorities());
        userDetailsManager.createUser(user);

        userDetailsDTO.setUsername(genRndUserName);
        userDetailsDTO.setPassword(rndPassword);
        userDetailsDTO.setEnabled(user.isEnabled());

        return userDetailsDTO;
    }

    @PostMapping("/admin/user/enabled")
    public UserDetailsDTO enableUser(@RequestBody UserDetailsDTO userDetailsDTO) {
        JdbcUserDetailsManager userDetailsManager = (JdbcUserDetailsManager) authenticationManagerBuilder.getDefaultUserDetailsService();

        UserDetails userDetails = userDetailsManager.loadUserByUsername(userDetailsDTO.getUsername());

        User user = new User(userDetails.getUsername(), userDetails.getPassword(), userDetailsDTO.isEnabled(), true, true, true, getGrantedAuthorities());
        userDetailsManager.updateUser(user);

        return userDetailsDTO;
    }

    @Transactional
    @DeleteMapping("/admin/user")
    public void deleteUser(@RequestBody UserDetailsDTO userDetailsDTO) {
        JdbcUserDetailsManager userDetailsManager = (JdbcUserDetailsManager) authenticationManagerBuilder.getDefaultUserDetailsService();

        userDetailsManager.deleteUser(userDetailsDTO.getUsername());

        bookingRepository.deleteByPrincipalName(userDetailsDTO.getUsername());
    }

    private String generateUsername(UserDetailsDTO userDetailsDTO) {
        return userDetailsDTO.getFirstName() + "-" + userDetailsDTO.getLastName() + "-" + rndUsername();
    }

    private String rndUsername() {
        return generateRandomString(6);
    }

    private String rndPassword() {
        return generateRandomString(8);
    }

    private String generateRandomString(int targetStringLength) {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    private List<GrantedAuthority> getGrantedAuthorities() {
        return Collections.singletonList(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return "ROLE_USER";
            }
        });
    }

}
