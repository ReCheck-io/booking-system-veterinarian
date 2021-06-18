package io.recheck.jobsapp.bookingvet.frontend.components.uoiGrid;

import io.recheck.jobsapp.bookingvet.backend.dto.UserDetailsDTO;

public interface UserDetailsGridListeners {

    void disableClickListener(UserDetailsDTO uoiNode);
    void deleteClickListener(UserDetailsDTO uoiNode);

}
