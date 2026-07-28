package com.grits.authenticationservice.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class SignupResponse {

    private UUID keycloakUserId;
}
