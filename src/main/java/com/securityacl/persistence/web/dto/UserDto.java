package com.securityacl.persistence.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class UserDto implements Serializable {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
}
