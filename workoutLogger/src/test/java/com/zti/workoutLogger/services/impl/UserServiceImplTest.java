package com.zti.workoutLogger.services.impl;

import com.zti.workoutLogger.models.User;
import com.zti.workoutLogger.models.dto.UserDto;
import com.zti.workoutLogger.repositories.UserRepository;
import com.zti.workoutLogger.services.UserService;
import com.zti.workoutLogger.services.WorkoutLoggerServiceTests;
import com.zti.workoutLogger.utils.exceptions.AlreadyExistsException;
import com.zti.workoutLogger.utils.exceptions.InvalidArgumentException;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;


class UserServiceImplTest extends WorkoutLoggerServiceTests {
    private static String MAIL = "mail@mail.com";
    private static String USERNAME = "username";
    private static String PASSWORD = "pass123";

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;

    @Test
    void shouldCreateUser() {
        // given
        UserDto newUserDto = new UserDto(MAIL, USERNAME, PASSWORD, PASSWORD);

        // when
        userService.createUser(newUserDto);

        // then
        List<User> allUsers = userRepository.findAll();
        assertAll(
                () -> assertThat(allUsers).hasSize(1),
                () -> assertThat(allUsers).extracting(User::getMail, User::getUsername)
                        .containsExactly(new Tuple(MAIL, USERNAME)),
                () -> assertThat(encoder.matches(PASSWORD, allUsers.iterator().next().getPassword())).isTrue()
        );
    }


    @ParameterizedTest
    @MethodSource("provideUsersDtoWithException")
    void shouldThrowWhenInvalidUser(UserDto userDto, Class<?> excClass, String excMsg) {
        UserDto newUserDto = new UserDto(MAIL, USERNAME, PASSWORD, PASSWORD);
        userService.createUser(newUserDto);

        assertThatThrownBy(() -> userService.createUser(userDto))
                .isExactlyInstanceOf(excClass)
                .hasMessage(excMsg);
    }

    private static Stream<Arguments> provideUsersDtoWithException() {
        return Stream.of(
                Arguments.of(new UserDto(MAIL, "newUser", PASSWORD, PASSWORD), AlreadyExistsException.class,
                        MAIL + " already exists"),
                Arguments.of(new UserDto("newMail", USERNAME, PASSWORD, PASSWORD), AlreadyExistsException.class,
                        USERNAME + " already exists"),
                Arguments.of(new UserDto(MAIL, USERNAME, PASSWORD, "wrongPass"), InvalidArgumentException.class,
                        "Provided passwords are different")
        );
    }
}