package com.ducle.user_service.model.dto;

import java.time.Instant;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserDTO(
                @NotBlank(message = "Email is required") @Email(message = "Email should be valid") String email,
                @NotNull(message = "AuthId is required") @Min(1) Long authId,
                Instant createdAt,
                Instant updatedAt

) {

        public UserDTO(String email, Long authId) {
                this(email, authId, null, null);
        }

}
