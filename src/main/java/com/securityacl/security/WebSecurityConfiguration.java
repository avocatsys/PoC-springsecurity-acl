package com.securityacl.security;

import com.securityacl.persistence.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class WebSecurityConfiguration {

        @Bean
        SecurityFilterChain springWebFilterChain(HttpSecurity http,
                                                 JWTTokenProvider tokenProvider) throws Exception {
            return http
                    .httpBasic(AbstractHttpConfigurer::disable)
                    .csrf(AbstractHttpConfigurer::disable)
                    .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .exceptionHandling(c -> c.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))

                    /*
                    .authorizeHttpRequests(authorize -> authorize
                            .requestMatchers("/auth/signin").permitAll()
                            .requestMatchers(HttpMethod.GET, "/vehicles/**").permitAll()
                            .requestMatchers(HttpMethod.DELETE, "/vehicles/**").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.GET, "/v1/vehicles/**").permitAll()
                            .anyRequest().authenticated()
                    )*/

                    .authorizeRequests(c -> c
                            .antMatchers(
                                    "/auth/signin",
                                    "/v2/api-docs",
                                    "/v3/api-docs",
                                    "/configuration/ui",
                                    "/swagger-resources/**",
                                    "**/health",
                                    "/swagger-ui/**",
                                    "/webjars/**",
                                    "/csrf/**").permitAll())
                    .authorizeRequests()
                    .anyRequest().authenticated()
                    .and()
                    .addFilterBefore(new JWTAuthenticationFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)
                    .build();
        }

        @Bean
        UserDetailsService customUserDetailsService(UserRepository users) {
            return (username) -> (UserDetails) users.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Username: " + username + " not found"));
        }

        @Bean
        AuthenticationManager customAuthenticationManager(UserDetailsService userDetailsService, PasswordEncoder encoder) {
            return authentication -> {
                String username = authentication.getPrincipal() + "";
                String password = authentication.getCredentials() + "";

                UserDetails user = userDetailsService.loadUserByUsername(username);

                if (!encoder.matches(password, user.getPassword())) {
                    throw new BadCredentialsException("Bad credentials");
                }

                if (!user.isEnabled()) {
                    throw new DisabledException("User account is not active");
                }

                return new UsernamePasswordAuthenticationToken(username, null, user.getAuthorities());
            };
        }
}
