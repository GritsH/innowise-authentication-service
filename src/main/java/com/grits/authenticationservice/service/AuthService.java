package com.grits.authenticationservice.service;

import com.grits.authenticationservice.client.UserServiceClient;
import com.grits.authenticationservice.exception.SignupException;
import com.grits.authenticationservice.mapper.AuthenticationMapper;
import com.grits.authenticationservice.model.request.InternalCreateUserRequest;
import com.grits.authenticationservice.model.request.LoginRequest;
import com.grits.authenticationservice.model.request.RefreshTokenRequest;
import com.grits.authenticationservice.model.request.SignupRequest;
import com.grits.authenticationservice.model.response.SignupResponse;
import com.grits.authenticationservice.model.response.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final KeycloakService keycloakService;

    private final UserServiceClient userServiceClient;

    private final AuthenticationMapper mapper;

    public SignupResponse signUp(SignupRequest request) {
        UUID keycloakId = keycloakService.createUser(request);
        keycloakService.assignRole(keycloakId, "USER");

        InternalCreateUserRequest internalCreateUserRequest = mapper.toUserServiceRequest(request);
        internalCreateUserRequest.setKeycloakUserId(keycloakId);
        try {
            userServiceClient.createUser(internalCreateUserRequest);
        } catch (Exception ex) {
            keycloakService.deleteUser(keycloakId);
            throw new SignupException(ex);
        }
        return new SignupResponse(keycloakId);
    }

    public TokenResponse login(LoginRequest request) {
        return keycloakService.login(request);
    }

    public TokenResponse refresh(RefreshTokenRequest request) {
        return keycloakService.refresh(request);
    }
}
