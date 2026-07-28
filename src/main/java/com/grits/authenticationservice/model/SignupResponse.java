package com.grits.authenticationservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class SignupResponse {

    private UUID keycloakUserId;
}
