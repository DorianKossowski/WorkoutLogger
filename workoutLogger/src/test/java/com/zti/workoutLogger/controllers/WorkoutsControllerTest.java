package com.zti.workoutLogger.controllers;

import com.zti.workoutLogger.models.Exercise;
import com.zti.workoutLogger.models.User;
import com.zti.workoutLogger.models.Workout;
import com.zti.workoutLogger.models.dto.ExerciseDto;
import com.zti.workoutLogger.models.dto.WorkoutDto;
import com.zti.workoutLogger.models.dto.WorkoutWithTrainingsDto;
import com.zti.workoutLogger.services.ExerciseService;
import com.zti.workoutLogger.services.WorkoutService;
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
import java.util.HashSet;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WorkoutsController.class)
class WorkoutsControllerTest extends WorkoutLoggerControllerTest {
    @MockBean
    private AuthenticatedUserGetter userGetter;
    @MockBean
    private ExerciseService exerciseService;
    @MockBean
    private WorkoutService workoutService;

    private static final User INIT_USER = new User();
    private static final Exercise EXERCISE_1 = new Exercise();
    private static final Exercise EXERCISE_2 = new Exercise();

    @BeforeAll
    static void beforeAll() {
        INIT_USER.setId(1L);

        EXERCISE_1.setId(2L);
        EXERCISE_1.setName("name");
        EXERCISE_2.setId(3L);
        EXERCISE_2.setName("name2");
    }

    @BeforeEach
    void beforeEach() {
        when(userGetter.get()).thenReturn(INIT_USER);
    }

    @Test
    void shouldReturnJsonList() throws Exception {
        Workout workout = new Workout();
        workout.setId(4L);
        workout.setName("workoutName");
        workout.setExercises(new HashSet<>(Arrays.asList(EXERCISE_1, EXERCISE_2)));

        when(exerciseService.getAllExercisesByUserId(INIT_USER.getId())).thenReturn(Arrays.asList(new ExerciseDto(EXERCISE_1),
                new ExerciseDto(EXERCISE_2)));
        when(workoutService.getAllWorkoutsByUserId(INIT_USER.getId())).thenReturn(Collections.singletonList(new WorkoutDto(workout)));

        mvc.perform(get("/workouts")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.workouts", hasSize(1)))
                .andExpect(jsonPath("$.workouts[0].id").value(workout.getId()))
                .andExpect(jsonPath("$.workouts[0].name").value(workout.getName()))
                .andExpect(jsonPath("$.workouts[0].exercisesId", hasSize(2)))
                .andExpect(jsonPath("$.workouts[0].exercises", hasSize(2)))
                .andExpect(jsonPath("$.exercises", hasSize(2)));

    }

    @Test
    void shouldReturnCorrectJson() throws Exception {
        WorkoutDto workoutDto = getWorkoutDto(EXERCISE_1);
        when(workoutService.createWorkout(any())).thenReturn(workoutDto);

        mvc.perform(post("/addWorkout")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(workoutDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(workoutDto.getId()))
                .andExpect(jsonPath("$.name").value(workoutDto.getName()))
                .andExpect(jsonPath("$.exercisesId[0]").value(EXERCISE_1.getId()))
                .andExpect(jsonPath("$.exercises[0].id").value(EXERCISE_1.getId()))
                .andExpect(jsonPath("$.exercises[0].name").value(EXERCISE_1.getName()));
    }

    @ParameterizedTest
    @MethodSource("provide409Exceptions")
    void shouldReturn409(Exception exception) throws Exception {
        when(workoutService.createWorkout(any())).thenThrow(exception);

        mvc.perform(post("/addWorkout")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(new WorkoutDto())))
                .andExpect(status().is(409));

    }

    private static Stream<Arguments> provide409Exceptions() {
        return Stream.of(
                Arguments.of(new InvalidArgumentException("")),
                Arguments.of(new AlreadyExistsException(""))
        );
    }

    @Test
    void shouldReturnJsonWithCorrectWorkout() throws Exception {
        WorkoutDto workoutDto = getWorkoutDto(EXERCISE_1);
        when(workoutService.getWorkoutById(anyLong())).thenReturn(new WorkoutWithTrainingsDto(workoutDto,
                Collections.emptyList()));

        mvc.perform(get("/workouts/4")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(workoutDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.workoutDto.id").value(workoutDto.getId()))
                .andExpect(jsonPath("$.workoutDto.name").value(workoutDto.getName()))
                .andExpect(jsonPath("$.workoutDto.exercisesId[0]").value(EXERCISE_1.getId()))
                .andExpect(jsonPath("$.workoutDto.exercises[0].id").value(EXERCISE_1.getId()))
                .andExpect(jsonPath("$.workoutDto.exercises[0].name").value(EXERCISE_1.getName()));
    }

    private WorkoutDto getWorkoutDto(Exercise exercise) {
        Workout workout = new Workout();
        workout.setId(4L);
        workout.setName("workoutName");
        workout.setExercises(Collections.singleton(EXERCISE_1));
        return new WorkoutDto(workout);
    }

    @Test
    void shouldReturnEmptyAfterDeletion() throws Exception {
        mvc.perform(delete("/workouts/delete/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").doesNotExist())
                .andExpect(status().is(204));
    }
}