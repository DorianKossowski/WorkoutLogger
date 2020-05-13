package com.zti.workoutLogger.services.impl;

import com.zti.workoutLogger.models.Exercise;
import com.zti.workoutLogger.models.Training;
import com.zti.workoutLogger.models.User;
import com.zti.workoutLogger.models.dto.*;
import com.zti.workoutLogger.repositories.TrainingRepository;
import com.zti.workoutLogger.repositories.UserRepository;
import com.zti.workoutLogger.services.*;
import com.zti.workoutLogger.utils.auth.AuthenticatedUserGetter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

class TrainingServiceImplTest extends WorkoutLoggerServiceTests {
    private static final String EXERCISE_NAME = "exerciseName";
    private static final String WORKOUT_NAME = "workoutName";
    private static final long INIT_USER_ID = 1L;

    @Autowired
    private UserService userService;
    @Autowired
    private ExerciseService exerciseService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WorkoutService workoutService;
    @Autowired
    private TrainingService trainingService;
    @Autowired
    private TrainingRepository trainingRepository;
    @MockBean
    private AuthenticatedUserGetter authenticatedUserGetter;

    private User initUser1;
    private ExerciseDto exercise1;
    private ExerciseDto exercise2;
    private WorkoutDto workout;

    @BeforeEach
    void beforeEach() {
        UserDto newExerciseDto = new UserDto("m@mail.com", "n", "pass123", "pass123");
        userService.createUser(newExerciseDto);

        initUser1 = userRepository.findById(INIT_USER_ID).orElseThrow(RuntimeException::new);

        when(authenticatedUserGetter.get()).thenReturn(initUser1);
        exercise1 = exerciseService.createExercise(new ExerciseDto(EXERCISE_NAME));
        exercise2 = exerciseService.createExercise(new ExerciseDto(EXERCISE_NAME + "2"));

        WorkoutDto workoutDto = new WorkoutDto(WORKOUT_NAME, new HashSet<>(Arrays.asList(exercise1.getId(),
                exercise2.getId())));
        workout = workoutService.createWorkout(workoutDto);
    }

    @Test
    void shouldCreateTraining() {
        // given
        TrainingDto trainingDto = new TrainingDto();
        trainingDto.setExercises(Collections.singletonList(new TrainingExerciseDto(getNewExercise(exercise1))));


        // when
        trainingService.createTraining(trainingDto, workout.getId());

        // then
        List<Training> allTrainings = trainingRepository.findAll();
        assertAll(
                () -> assertThat(allTrainings).hasSize(1),
                () -> assertThat(allTrainings).extracting(training -> training.getWorkout().getId())
                        .isEqualTo(Collections.singletonList(workout.getId()))
        );
    }

    private Exercise getNewExercise(ExerciseDto exerciseDto) {
        Exercise exercise = new Exercise();
        exercise.setId(exerciseDto.getId());
        exercise.setName(exerciseDto.getName());
        exercise.setUser(authenticatedUserGetter.get());
        return exercise;
    }
}