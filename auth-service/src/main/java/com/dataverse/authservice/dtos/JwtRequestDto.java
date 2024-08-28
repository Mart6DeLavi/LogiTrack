package com.dataverse.authservice.dtos;

import com.dataverse.authservice.entities.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Value;

import java.io.Serializable;


@Getter
@Setter
@RequiredArgsConstructor
public class JwtRequestDto {
    private String username;
    private String password;
}