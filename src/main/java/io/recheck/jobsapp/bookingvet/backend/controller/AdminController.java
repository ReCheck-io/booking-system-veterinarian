package io.recheck.jobsapp.bookingvet.backend.controller;

import io.recheck.jobsapp.bookingvet.backend.dto.UserDetailsDTO;
import io.recheck.jobsapp.bookingvet.backend.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/admin/users")
    public List<UserDetailsDTO> getUsers() {
        return adminService.getUsers();
    }

    @PostMapping("/admin/user")
    public UserDetailsDTO createUser(@RequestBody UserDetailsDTO userDetailsDTO) throws Exception {
        return adminService.createUser(userDetailsDTO);
    }

    @PostMapping("/admin/user/enabled")
    public UserDetailsDTO enableUser(@RequestBody UserDetailsDTO userDetailsDTO) {
        return adminService.enableUser(userDetailsDTO);
    }

    @Transactional
    @DeleteMapping("/admin/user")
    public void deleteUser(@RequestBody UserDetailsDTO userDetailsDTO) {
        adminService.deleteUser(userDetailsDTO);
    }



}
