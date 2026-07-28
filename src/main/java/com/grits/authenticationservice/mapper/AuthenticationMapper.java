package com.grits.authenticationservice.mapper;

import com.grits.authenticationservice.model.request.InternalCreateUserRequest;
import com.grits.authenticationservice.model.request.SignupRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthenticationMapper {

    InternalCreateUserRequest toUserServiceRequest(SignupRequest request);
}
