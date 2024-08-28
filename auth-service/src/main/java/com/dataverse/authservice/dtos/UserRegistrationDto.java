package com.dataverse.authservice.dtos;

import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link com.dataverse.authservice.entities.User}
 */
@Getter
@Setter
@RequiredArgsConstructor
public class UserRegistrationDto implements Serializable {
    private String username;
    private String firstName;
    private String secondName;
    private String password;
    private String confirmPassword;
    private String email;
    private String phone;
    private String address;
}