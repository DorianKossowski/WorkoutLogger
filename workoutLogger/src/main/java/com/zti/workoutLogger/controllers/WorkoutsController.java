package com.zti.workoutLogger.controllers;

import com.zti.workoutLogger.models.dto.ExerciseDto;
import com.zti.workoutLogger.models.dto.WorkoutDto;
import com.zti.workoutLogger.models.dto.WorkoutsWithExercisesDto;
import com.zti.workoutLogger.services.ExerciseService;
import com.zti.workoutLogger.services.WorkoutService;
import com.zti.workoutLogger.utils.auth.AuthenticatedUserGetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class WorkoutsController {
    @Autowired
    private AuthenticatedUserGetter userGetter;
    @Autowired
    private WorkoutService workoutService;
    @Autowired
    private ExerciseService exerciseService;

    @GetMapping("/workouts")
    public WorkoutsWithExercisesDto showCoachesList(Authentication authentication) {
        long userId = userGetter.get().getId();
        List<ExerciseDto> exercises = exerciseService.getAllExercisesByUserId(userId);
        List<WorkoutDto> workouts = workoutService.getAllWorkoutsByUserId(userId);
        return new WorkoutsWithExercisesDto(workouts, exercises);
    }

    @PostMapping("/addWorkout")
    public WorkoutDto addExercise(@RequestBody WorkoutDto workoutDto) {
        return workoutService.createWorkout(workoutDto);
    }
}