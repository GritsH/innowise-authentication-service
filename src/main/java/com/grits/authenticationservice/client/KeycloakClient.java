package com.grits.authenticationservice.client;

import com.grits.authenticationservice.model.response.TokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "keycloak-auth",
        url = "${keycloak.server-url}"
)
public interface KeycloakClient {

    @PostMapping(
            value = "/realms/{realm}/protocol/openid-connect/token",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    TokenResponse login(@PathVariable String realm, @RequestBody MultiValueMap<String, String> form);
}
