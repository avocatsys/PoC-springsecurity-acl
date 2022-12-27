package com.securityacl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SecurityAclApplication {

	/**
	 * <section>
	 * Existem muitos algoritmos de hashing diferentes.
	 * O mais comumente utilizado é o BCrypt, sendo recomendado para um hashing seguro.
	 * Confira este artigo (em inglês) para obter mais informações sobre o tópico.
	 * </br>
	 * Chamaremos os métodos nesse bean quando precisarmos fazer o hashing de uma senha.
	 * </section>
	 *
	 * @return BCryptPasswordEncoder
	 */
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public static void main(String[] args) {
		SpringApplication.run(SecurityAclApplication.class, args);
	}

}
