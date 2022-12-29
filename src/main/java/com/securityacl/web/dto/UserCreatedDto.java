package com.securityacl.web.dto;

import com.securityacl.persistence.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class UserCreatedDto implements Serializable {
    //private UUID id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;

    public static UserCreatedDto of(User user) {
        UserCreatedDto userCreatedDto = new UserCreatedDto();
        //userCreatedDto.setId(user.getId());
        userCreatedDto.setEmail(user.getEmail());
        userCreatedDto.setFirstName(user.getFirstName());
        userCreatedDto.setLastName(user.getLastName());
        return userCreatedDto;
    }
}
