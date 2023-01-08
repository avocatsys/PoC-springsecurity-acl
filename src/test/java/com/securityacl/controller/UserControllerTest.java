package com.securityacl.controller;

import com.securityacl.web.dto.UserCreatedDto;
import com.securityacl.web.dto.UserDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Test
    void createUser_then_201() throws Exception {

        URI uri = new URI("http://localhost:" + port + "/api/v1/users");

        var random = UUID.randomUUID();

        var userDto = new UserDto();
        userDto.setEmail("owtest+" + random + "@gmail.com");
        userDto.setPassword("12345678");
        userDto.setUsername("owtest" + random + "@gmail.com");
        userDto.setFirstName("owteste" + random);
        userDto.setLastName("yellow" + random);

        HttpEntity<Object> request = new HttpEntity<>(userDto);

        ResponseEntity<UserCreatedDto> userResponse = restTemplate.postForEntity(uri, request, UserCreatedDto.class);

        Assertions.assertEquals(userResponse.getStatusCodeValue(), 201);
    }


}
