package com.grits.authenticationservice.model.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class InternalCreateUserRequest {

    private String email;

    private String name;

    private String surname;

    private LocalDate birthDate;

    private UUID keycloakUserId;
}
