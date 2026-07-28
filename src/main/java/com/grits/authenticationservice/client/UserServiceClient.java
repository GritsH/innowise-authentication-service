package com.grits.authenticationservice.client;

import com.grits.authenticationservice.model.request.InternalCreateUserRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "user-service",
        url = "${services.user-service.url}"
)
public interface UserServiceClient {

    @PostMapping("/v1/users")
    void createUser(@RequestBody InternalCreateUserRequest request);

}
