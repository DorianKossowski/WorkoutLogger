package com.zti.workoutLogger.services.impl;

import com.zti.workoutLogger.models.Exercise;
import com.zti.workoutLogger.models.User;
import com.zti.workoutLogger.models.dto.ExerciseDto;
import com.zti.workoutLogger.models.dto.UserDto;
import com.zti.workoutLogger.repositories.ExerciseRepository;
import com.zti.workoutLogger.repositories.UserRepository;
import com.zti.workoutLogger.services.ExerciseService;
import com.zti.workoutLogger.services.UserService;
import com.zti.workoutLogger.services.WorkoutLoggerServiceTests;
import com.zti.workoutLogger.utils.auth.AuthenticatedUserGetter;
import com.zti.workoutLogger.utils.exceptions.AlreadyExistsException;
import com.zti.workoutLogger.utils.exceptions.InvalidArgumentExceptions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

class ExerciseServiceImplTest extends WorkoutLoggerServiceTests {
    private static final String NAME = "exerciseName";
    private static final long INIT_USER_ID = 1L;
    private static final long INIT_USER_ID_2 = 2L;

    @Autowired
    private UserService userService;
    @Autowired
    private ExerciseRepository exerciseRepository;
    @Autowired
    private ExerciseService exerciseService;
    @Autowired
    private UserRepository userRepository;
    @MockBean
    private AuthenticatedUserGetter authenticatedUserGetter;

    private User initUser1;
    private User initUser2;

    @BeforeEach
    void beforeEach() {
        UserDto newExerciseDto = new UserDto("m@mail.com", "n", "pass123", "pass123");
        UserDto newExerciseDto2 = new UserDto("m2@mail.com", "n2", "pass123", "pass123");
        userService.createUser(newExerciseDto);
        userService.createUser(newExerciseDto2);

        initUser1 = userRepository.findById(INIT_USER_ID).orElseThrow(RuntimeException::new);
        initUser2 = userRepository.findById(INIT_USER_ID_2).orElseThrow(RuntimeException::new);

        when(authenticatedUserGetter.get()).thenReturn(initUser1);
    }

    @Test
    void shouldCreateExercise() {
        // given
        ExerciseDto exerciseDto = new ExerciseDto(NAME);

        //when
        exerciseService.createExercise(exerciseDto);

        // then
        List<Exercise> allExercises = exerciseRepository.findAll();
        assertAll(
                () -> assertThat(allExercises).hasSize(1),
                () -> assertThat(allExercises).extracting(Exercise::getName, e -> e.getUser().getId())
                        .containsExactly(new Tuple(NAME, INIT_USER_ID))
        );
    }

    @ParameterizedTest
    @MethodSource("provideExercisesDtoWithException")
    void shouldThrowWhenInvalidUser(ExerciseDto exerciseDto, Class<?> excClass, String excMsg) {
        exerciseService.createExercise(new ExerciseDto(NAME));

        assertThatThrownBy(() -> exerciseService.createExercise(exerciseDto))
                .isExactlyInstanceOf(excClass)
                .hasMessage(excMsg);
    }

    private static Stream<Arguments> provideExercisesDtoWithException() {
        return Stream.of(
                Arguments.of(new ExerciseDto(NAME), AlreadyExistsException.class, NAME + " already exists"),
                Arguments.of(new ExerciseDto(""), InvalidArgumentExceptions.class, "Name cannot be empty")
        );
    }

    @Test
    void shouldGetUserExercises() {
        // given
        ExerciseDto exerciseDto = new ExerciseDto(NAME);
        ExerciseDto exerciseDto2 = new ExerciseDto(NAME + "2");
        exerciseService.createExercise(exerciseDto);
        exerciseService.createExercise(exerciseDto2);

        //when
        List<ExerciseDto> userExercises = exerciseService.getAllExercisesByUserId(INIT_USER_ID);

        // then
        assertAll(
                () -> assertThat(userExercises).hasSize(2),
                () -> assertThat(userExercises).extracting(ExerciseDto::getName)
                        .containsExactly(NAME, NAME + "2")
        );
    }

    @Test
    void shouldGetUserExercisesWhenManyUsers() {
        // given
        when(authenticatedUserGetter.get()).thenReturn(initUser1, initUser2);
        ExerciseDto exerciseDto = new ExerciseDto(NAME);
        ExerciseDto exerciseDto2 = new ExerciseDto(NAME + "2");
        exerciseService.createExercise(exerciseDto);
        exerciseService.createExercise(exerciseDto2);

        //when
        List<ExerciseDto> userExercises = exerciseService.getAllExercisesByUserId(INIT_USER_ID);

        // then
        assertAll(
                () -> assertThat(userExercises).hasSize(1),
                () -> assertThat(userExercises).extracting(ExerciseDto::getName)
                        .containsExactly(NAME)
        );
    }
}