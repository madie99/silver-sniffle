package com.example.BlogApplication.SecurityConfig;

import com.example.BlogApplication.Service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig {

    //authenticationProvider bean definition
    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserService userService) {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService); //set the custom user details service
        auth.setPasswordEncoder(customPasswordEncoder()); //set the password encoder - bcrypt
        return auth;
    }
    @Bean
    public PasswordEncoder customPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(configurer ->
                        configurer
                                .requestMatchers("/").hasRole("")
                                .requestMatchers("/leaders/**").hasRole("AUTHOR")
                                .requestMatchers("/systems/**").hasRole("ADMIN")
                                .requestMatchers("/open_signup").permitAll()
                                .requestMatchers("/posts").permitAll()
                                .requestMatchers("/searchPosts").permitAll()
                                .requestMatchers("/sortPosts").permitAll()
                                .requestMatchers("/filterPosts").permitAll()
                                .requestMatchers("/signup").permitAll()
                                .anyRequest().authenticated()

                )
                .formLogin(form ->
                        form
                                .loginPage("/showMyLoginPage")
                                .loginProcessingUrl("/authenticateTheUser")
                                .permitAll()
                )
                .logout(logout -> logout.permitAll()
                )
                .exceptionHandling(configurer ->
                        configurer.accessDeniedPage("/access-denied")
                );

        return http.build();
    }



}