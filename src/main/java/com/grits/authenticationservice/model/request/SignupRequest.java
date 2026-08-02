package com.grits.authenticationservice.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class SignupRequest {

    @NotBlank(message = "Name is required")
    @Size(max = 30, message = "Name can be no more than 30 characters long")
    private String name;

    @NotBlank(message = "Surname is required")
    @Size(max = 30, message = "Surname can be no more than 30 characters long")
    private String surname;

    @NotNull(message = "Birthdate required")
    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Size(max = 255, message = "Email must be less than 255 characters long")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;
}
