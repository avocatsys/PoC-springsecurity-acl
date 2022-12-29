package com.securityacl.web.controller;

import com.securityacl.persistence.entity.User;
import com.securityacl.persistence.repository.UserRepository;
import com.securityacl.web.dto.UserCreatedDto;
import com.securityacl.web.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    @PostMapping
    public ResponseEntity<UserCreatedDto> create(@RequestBody UserDto userDto) {

        /**
         * <section>
         *  A criptografia da senha é possível fazer isso no controller,
         *  mas é uma prática recomendada colocar essa lógica na classe do serviço.
         * </section>
         */
        userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));

        var userCreated = userRepository.save(User.of(userDto));

        return ResponseEntity.status(201).body(UserCreatedDto.of(userCreated));
    }
}
