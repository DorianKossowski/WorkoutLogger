package com.zti.workoutLogger.controllers;

import com.zti.workoutLogger.models.Exercise;
import com.zti.workoutLogger.models.dto.TrainingExerciseDto;
import com.zti.workoutLogger.services.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
public class TrainingController {
    @Autowired
    private WorkoutService workoutService;

    @GetMapping("/workouts/{id}/training")
    public List<TrainingExerciseDto> getNewTrainingExercises(@PathVariable long id) {
        Set<Exercise> exercises = workoutService.getWorkoutById(id).getExercises();
        return exercises.stream()
                .map(TrainingExerciseDto::new)
                .collect(Collectors.toList());
    }
}