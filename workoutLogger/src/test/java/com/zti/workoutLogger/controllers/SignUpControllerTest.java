package com.zti.workoutLogger.controllers;

import com.zti.workoutLogger.models.dto.UserDto;
import com.zti.workoutLogger.services.UserService;
import com.zti.workoutLogger.utils.exceptions.AlreadyExistsException;
import com.zti.workoutLogger.utils.exceptions.InvalidArgumentExceptions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SignUpController.class)
class SignUpControllerTest extends WorkoutLoggerControllerTest {
    @MockBean
    private UserService userService;

    @Test
    void shouldReturnUserJson() throws Exception {
        UserDto userDto = new UserDto("mail", "name", "pass", "pass");

        mvc.perform(post("/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mail").value(userDto.getMail()))
                .andExpect(jsonPath("$.username").value(userDto.getUsername()))
                .andExpect(jsonPath("$.password").value(userDto.getPassword()))
                .andExpect(jsonPath("$.repPassword").value(userDto.getRepPassword()));
    }

    @ParameterizedTest
    @MethodSource("provide409Exceptions")
    void shouldReturn409(Exception exception) throws Exception {
        doThrow(exception).when(userService).createUser(any());

        mvc.perform(post("/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(new UserDto())))
                .andExpect(status().is(409));

    }

    private static Stream<Arguments> provide409Exceptions() {
        return Stream.of(
                Arguments.of(new InvalidArgumentExceptions("")),
                Arguments.of(new AlreadyExistsException(""))
        );
    }
}