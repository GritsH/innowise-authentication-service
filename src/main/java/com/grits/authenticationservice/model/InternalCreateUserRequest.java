package com.grits.authenticationservice.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class InternalCreateUserRequest {

    private String email;

    private String name;

    private String surname;

    private LocalDate birthDate;

    private UUID keycloakUserId;
}
