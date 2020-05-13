package com.zti.workoutLogger.controllers;

import com.zti.workoutLogger.models.Exercise;
import com.zti.workoutLogger.models.User;
import com.zti.workoutLogger.models.Workout;
import com.zti.workoutLogger.models.dto.WorkoutDto;
import com.zti.workoutLogger.models.dto.WorkoutWithTrainingsDto;
import com.zti.workoutLogger.services.TrainingService;
import com.zti.workoutLogger.services.WorkoutService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TrainingController.class)
class TrainingControllerTest extends WorkoutLoggerControllerTest {
    @MockBean
    private WorkoutService workoutService;
    @MockBean
    private TrainingService trainingService;

    private static final User INIT_USER = new User();
    private static final Exercise EXERCISE_1 = new Exercise();

    @BeforeAll
    static void beforeAll() {
        INIT_USER.setId(1L);

        EXERCISE_1.setId(2L);
        EXERCISE_1.setName("name");
    }

    @Test
    void shouldReturnJsonForNewTraining() throws Exception {
        WorkoutDto workoutDto = getWorkoutDto(EXERCISE_1);
        when(workoutService.getWorkoutById(anyLong())).thenReturn(new WorkoutWithTrainingsDto(workoutDto,
                Collections.emptyList()));

        mvc.perform(get("/workouts/3/training"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.exercises[0].id").value(EXERCISE_1.getId()))
                .andExpect(jsonPath("$.exercises[0].name").value(EXERCISE_1.getName()))
                .andExpect(jsonPath("$.exercises[0].sets").isEmpty());
    }

    private WorkoutDto getWorkoutDto(Exercise exercise) {
        Workout workout = new Workout();
        workout.setId(3L);
        workout.setName("workoutName");
        workout.setExercises(Collections.singleton(EXERCISE_1));
        return new WorkoutDto(workout);
    }
}