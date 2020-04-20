package com.zti.workoutLogger.services.impl;

import com.zti.workoutLogger.models.dto.UserDto;
import com.zti.workoutLogger.services.UserService;
import com.zti.workoutLogger.services.WorkoutLoggerServiceTests;
import com.zti.workoutLogger.utils.exceptions.InvalidCredentialsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class UserDetailsServiceImplTest extends WorkoutLoggerServiceTests {
    private static String MAIL = "mail@mail.com";
    private static String USERNAME = "username";
    private static String PASSWORD = "pass123";

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder encoder;

    @BeforeEach
    void beforeEach() {
        UserDto newUserDto = new UserDto(MAIL, USERNAME, PASSWORD, PASSWORD);
        userService.createUser(newUserDto);
    }

    @Test
    void shouldCreateUser() {
        UserDetails userDetails = userDetailsService.loadUserByUsername(USERNAME);

        assertAll(
                () -> assertThat(userDetails.getUsername()).isEqualTo(USERNAME),
                () -> assertThat(encoder.matches(PASSWORD, userDetails.getPassword())).isTrue()
        );
    }

    @Test
    void shouldThrowWhenNotExists() {
        assertThatThrownBy(() -> userDetailsService.loadUserByUsername("wrongUsername"))
                .isExactlyInstanceOf(InvalidCredentialsException.class)
                .hasMessage(InvalidCredentialsException.MESSAGE);

    }
}