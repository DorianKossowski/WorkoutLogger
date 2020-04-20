package com.zti.workoutLogger.services.impl;

import com.zti.workoutLogger.models.Exercise;
import com.zti.workoutLogger.models.User;
import com.zti.workoutLogger.models.Workout;
import com.zti.workoutLogger.models.dto.ExerciseDto;
import com.zti.workoutLogger.models.dto.UserDto;
import com.zti.workoutLogger.models.dto.WorkoutDto;
import com.zti.workoutLogger.repositories.UserRepository;
import com.zti.workoutLogger.repositories.WorkoutRepository;
import com.zti.workoutLogger.services.ExerciseService;
import com.zti.workoutLogger.services.UserService;
import com.zti.workoutLogger.services.WorkoutLoggerServiceTests;
import com.zti.workoutLogger.services.WorkoutService;
import com.zti.workoutLogger.utils.auth.AuthenticatedUserGetter;
import com.zti.workoutLogger.utils.exceptions.AlreadyExistsException;
import com.zti.workoutLogger.utils.exceptions.InvalidArgumentExceptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

class WorkoutServiceImplTest extends WorkoutLoggerServiceTests {
    private static final String EXERCISE_NAME = "exerciseName";
    private static final String WORKOUT_NAME = "workoutName";
    private static final long INIT_USER_ID = 1L;
    private static final long INIT_USER_ID_2 = 2L;

    @Autowired
    private UserService userService;
    @Autowired
    private ExerciseService exerciseService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WorkoutService workoutService;
    @Autowired
    private WorkoutRepository workoutRepository;
    @MockBean
    private AuthenticatedUserGetter authenticatedUserGetter;

    private User initUser1;
    private User initUser2;
    private ExerciseDto exercise1;
    private ExerciseDto exercise2;

    @BeforeEach
    void beforeEach() {
        UserDto newExerciseDto = new UserDto("m@mail.com", "n", "pass123", "pass123");
        UserDto newExerciseDto2 = new UserDto("m2@mail.com", "n2", "pass123", "pass123");
        userService.createUser(newExerciseDto);
        userService.createUser(newExerciseDto2);

        initUser1 = userRepository.findById(INIT_USER_ID).orElseThrow(RuntimeException::new);
        initUser2 = userRepository.findById(INIT_USER_ID_2).orElseThrow(RuntimeException::new);

        when(authenticatedUserGetter.get()).thenReturn(initUser1);
        exercise1 = exerciseService.createExercise(new ExerciseDto(EXERCISE_NAME));
        exercise2 = exerciseService.createExercise(new ExerciseDto(EXERCISE_NAME + "2"));
    }

    @SuppressWarnings("unchecked")
    @Test
    void shouldCreateWorkout() {
        // given
        WorkoutDto workoutDto = new WorkoutDto(WORKOUT_NAME, new HashSet<>(Arrays.asList(exercise1.getId(),
                exercise2.getId())));

        //when
        workoutService.createWorkout(workoutDto);

        // then
        List<Workout> allWorkouts = workoutRepository.findAll();
        assertAll(
                () -> assertThat(allWorkouts).hasSize(1),
                () -> assertThat(allWorkouts).extracting(Workout::getName).containsExactly(WORKOUT_NAME),
                () -> assertThat(allWorkouts).extracting(w -> w.getExercises().stream().map(Exercise::getId).sorted()
                        .collect(Collectors.toList()))
                        .contains(Arrays.asList(exercise1.getId(), exercise2.getId()))
        );
    }

    @ParameterizedTest
    @MethodSource("provideWorkoutsDtoWithException")
    void shouldThrowWhenInvalidUser(WorkoutDto workoutDto, Class<?> excClass, String excMsg) {
        workoutService.createWorkout(new WorkoutDto(WORKOUT_NAME, Collections.singleton(exercise1.getId())));

        assertThatThrownBy(() -> workoutService.createWorkout(workoutDto))
                .isExactlyInstanceOf(excClass)
                .hasMessage(excMsg);
    }

    private static Stream<Arguments> provideWorkoutsDtoWithException() {
        return Stream.of(
                Arguments.of(new WorkoutDto(WORKOUT_NAME, Collections.singleton(1L)), AlreadyExistsException.class,
                        WORKOUT_NAME + " already exists"),
                Arguments.of(new WorkoutDto("", Collections.singleton(1L)), InvalidArgumentExceptions.class,
                        "Name cannot be empty"),
                Arguments.of(new WorkoutDto(WORKOUT_NAME, Collections.emptySet()), InvalidArgumentExceptions.class,
                        "Select at least one exercise")
        );
    }

    @SuppressWarnings("unchecked")
    @Test
    void shouldGetUserWorkouts() {
        // given
        WorkoutDto workoutDto = new WorkoutDto(WORKOUT_NAME, Collections.singleton(exercise1.getId()));
        WorkoutDto workoutDto2 = new WorkoutDto(WORKOUT_NAME + "2", Collections.singleton(exercise2.getId()));
        workoutService.createWorkout(workoutDto);
        workoutService.createWorkout(workoutDto2);

        //when
        List<WorkoutDto> userWorkouts = workoutService.getAllWorkoutsByUserId(INIT_USER_ID);

        // then
        assertAll(
                () -> assertThat(userWorkouts).hasSize(2),
                () -> assertThat(userWorkouts).extracting(WorkoutDto::getName)
                        .containsExactly(WORKOUT_NAME, WORKOUT_NAME + "2"),
                () -> assertThat(userWorkouts).extracting(WorkoutDto::getExercisesId)
                        .containsExactly(Collections.singleton(exercise1.getId()),
                                Collections.singleton(exercise2.getId()))
        );


    }

    @Test
    void shouldGetUserExercisesWhenManyUsers() {
        // given
        when(authenticatedUserGetter.get()).thenReturn(initUser2);
        ExerciseDto otherExercise = exerciseService.createExercise(new ExerciseDto(EXERCISE_NAME + "other"));
        WorkoutDto workoutDto = new WorkoutDto(WORKOUT_NAME, Collections.singleton(exercise1.getId()));
        WorkoutDto otherWorkoutDto = new WorkoutDto(WORKOUT_NAME + "other",
                Collections.singleton(otherExercise.getId()));
        workoutService.createWorkout(workoutDto);
        workoutService.createWorkout(otherWorkoutDto);

        //when
        List<WorkoutDto> userWorkouts = workoutService.getAllWorkoutsByUserId(INIT_USER_ID);

        // then
        assertAll(
                () -> assertThat(userWorkouts).hasSize(1),
                () -> assertThat(userWorkouts).extracting(WorkoutDto::getName)
                        .containsExactly(WORKOUT_NAME)
        );
    }
}