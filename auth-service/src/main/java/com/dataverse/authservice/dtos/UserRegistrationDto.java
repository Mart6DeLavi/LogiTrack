package com.dataverse.authservice.dtos;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.dataverse.authservice.entities.User}
 */
@Value
@Getter
@Setter
@RequiredArgsConstructor
public class UserRegistrationDto implements Serializable {
    String username;
    String secondName;
    String password;
    String confirmPassword;
    String email;
}