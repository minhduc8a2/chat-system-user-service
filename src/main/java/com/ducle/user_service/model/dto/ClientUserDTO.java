package com.ducle.user_service.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ClientUserDTO(
        @NotBlank(message = "Email is required") @Email(message = "Email should be valid") String email,

        String createdAt,
        String updatedAt

) {

}
