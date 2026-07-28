package com.grits.authenticationservice.service;

import com.grits.authenticationservice.exception.InvalidCredentialsException;
import com.grits.authenticationservice.exception.UserAlreadyExistsException;
import com.grits.authenticationservice.model.request.LoginRequest;
import com.grits.authenticationservice.model.request.SignupRequest;
import com.grits.authenticationservice.model.response.TokenResponse;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class KeycloakService {

    private final Keycloak keycloak;

    private final RestClient keycloakRestClient;

    @Value("${keycloak.server-url}")
    private String serverUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.client-id}")
    private String clientId;

    public UUID createUser(SignupRequest request) {
        UserRepresentation user = getUserRepresentation(request);
        Response response = keycloak
                .realm(realm)
                .users()
                .create(user);

        if (response.getStatus() == 409) {
            throw new UserAlreadyExistsException(request.getEmail());
        }
        if (response.getStatus() != 201) {
            throw new RuntimeException("Failed creating Keycloak user");
        }

        String id = CreatedResponseUtil.getCreatedId(response);
        return UUID.fromString(id);
    }

    public TokenResponse login(LoginRequest request) {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("client_id", clientId);
        form.add("username", request.getEmail());
        form.add("password", request.getPassword());
        form.add("grant_type", "password");

        try {
            return keycloakRestClient.post()
                    .uri(serverUrl + "/realms/" + realm + "/protocol/openid-connect/token")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(form)
                    .retrieve()
                    .body(TokenResponse.class);
        } catch (HttpClientErrorException.Unauthorized e) {
            throw new InvalidCredentialsException();
        }
    }

    private static UserRepresentation getUserRepresentation(SignupRequest request) {
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setTemporary(false);
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(request.getPassword());

        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setUsername(request.getEmail());
        user.setEmail(request.getEmail());
        user.setFirstName(request.getName());
        user.setLastName(request.getSurname());
        user.setCredentials(List.of(credential));
        return user;
    }

    public void assignRole(UUID id, String role) {
        RoleRepresentation roleRep =
                keycloak.realm(realm)
                        .roles()
                        .get(role)
                        .toRepresentation();

        keycloak.realm(realm)
                .users()
                .get(id.toString())
                .roles()
                .realmLevel()
                .add(List.of(roleRep));
    }

    public void deleteUser(UUID id) {
        keycloak
                .realm(realm)
                .users()
                .delete(id.toString());
    }
}
