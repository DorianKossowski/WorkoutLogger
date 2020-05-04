package com.zti.workoutLogger.controllers;

import com.zti.workoutLogger.models.Exercise;
import com.zti.workoutLogger.models.User;
import com.zti.workoutLogger.models.dto.ExerciseDto;
import com.zti.workoutLogger.services.ExerciseService;
import com.zti.workoutLogger.utils.auth.AuthenticatedUserGetter;
import com.zti.workoutLogger.utils.exceptions.AlreadyExistsException;
import com.zti.workoutLogger.utils.exceptions.InvalidArgumentException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ExercisesController.class)
class ExercisesControllerTest extends WorkoutLoggerControllerTest {
    @MockBean
    private AuthenticatedUserGetter userGetter;
    @MockBean
    private ExerciseService exerciseService;

    private static final User INIT_USER = new User();

    @BeforeAll
    static void beforeAll() {
        INIT_USER.setId(1L);
    }

    @BeforeEach
    void beforeEach() {
        when(userGetter.get()).thenReturn(INIT_USER);
    }

    @Test
    void shouldReturnJsonList() throws Exception {
        ExerciseDto exerciseDto = new ExerciseDto("name");
        ExerciseDto exerciseDto2 = new ExerciseDto("name2");
        when(exerciseService.getAllExercisesByUserId(INIT_USER.getId())).thenReturn(Arrays.asList(exerciseDto,
                exerciseDto2));

        mvc.perform(get("/exercises")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value(exerciseDto.getName()))
                .andExpect(jsonPath("$[1].name").value(exerciseDto2.getName()));
    }

    @Test
    void shouldReturnEmptyJson() throws Exception {
        when(exerciseService.getAllExercisesByUserId(INIT_USER.getId())).thenReturn(Collections.emptyList());

        mvc.perform(get("/exercises")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void shouldReturnJsonWithIdAndName() throws Exception {
        Exercise exercise = new Exercise();
        exercise.setId(2L);
        exercise.setUser(INIT_USER);
        exercise.setName("name");

        ExerciseDto result = new ExerciseDto(exercise);
        when(exerciseService.createExercise(any())).thenReturn(result);

        mvc.perform(post("/addExercise")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(result)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(exercise.getId()))
                .andExpect(jsonPath("$.name").value(exercise.getName()));
    }

    @ParameterizedTest
    @MethodSource("provide409Exceptions")
    void shouldReturn409(Exception exception) throws Exception {
        when(exerciseService.createExercise(any())).thenThrow(exception);

        mvc.perform(post("/addExercise")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString("")))
                .andExpect(status().is(409));

    }

    private static Stream<Arguments> provide409Exceptions() {
        return Stream.of(
                Arguments.of(new InvalidArgumentException("")),
                Arguments.of(new AlreadyExistsException(""))
        );
    }

    @Test
    void shouldReturnJsonWithCorrectExercise() throws Exception {
        Exercise exercise = new Exercise();
        exercise.setId(2L);
        exercise.setUser(INIT_USER);
        exercise.setName("name");

        ExerciseDto result = new ExerciseDto(exercise);
        when(exerciseService.getExerciseById(anyLong())).thenReturn(result);

        mvc.perform(get("/exercises/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(result)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(exercise.getId()))
                .andExpect(jsonPath("$.name").value(exercise.getName()));
    }

    @Test
    void shouldReturnJsonWithIdAndUpdatedName() throws Exception {
        Exercise exercise = new Exercise();
        exercise.setId(2L);
        exercise.setUser(INIT_USER);
        exercise.setName("name");

        ExerciseDto result = new ExerciseDto(exercise);
        when(exerciseService.editExercise(any(), anyLong())).thenReturn(result);

        mvc.perform(put("/exercises/edit/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(result)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(exercise.getId()))
                .andExpect(jsonPath("$.name").value(exercise.getName()));
    }
}