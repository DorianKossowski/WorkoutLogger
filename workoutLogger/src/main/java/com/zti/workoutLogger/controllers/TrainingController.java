package com.zti.workoutLogger.controllers;

import com.zti.workoutLogger.models.Exercise;
import com.zti.workoutLogger.models.dto.TrainingDto;
import com.zti.workoutLogger.models.dto.TrainingExerciseDto;
import com.zti.workoutLogger.services.TrainingService;
import com.zti.workoutLogger.services.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
public class TrainingController {
    @Autowired
    private WorkoutService workoutService;
    @Autowired
    private TrainingService trainingService;

    @GetMapping("/workouts/{workoutId}/training")
    public List<TrainingExerciseDto> getNewTrainingExercises(@PathVariable long workoutId) {
        Set<Exercise> exercises = workoutService.getWorkoutById(workoutId).getWorkout().getExercises();
        return exercises.stream()
                .map(TrainingExerciseDto::new)
                .collect(Collectors.toList());
    }

    @PostMapping("/workouts/{workoutId}/addTraining")
    public TrainingDto addTraining(@RequestBody TrainingDto trainingDto, @PathVariable long workoutId) {
        return trainingService.createTraining(trainingDto, workoutId);
    }
}