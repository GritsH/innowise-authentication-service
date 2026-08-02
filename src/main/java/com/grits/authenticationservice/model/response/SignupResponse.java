package com.grits.authenticationservice.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class SignupResponse {

    private UUID keycloakUserId;
}
