package com.zti.workoutLogger.controllers;

import com.zti.workoutLogger.models.Exercise;
import com.zti.workoutLogger.models.dto.TrainingDto;
import com.zti.workoutLogger.models.dto.TrainingExerciseDto;
import com.zti.workoutLogger.services.TrainingService;
import com.zti.workoutLogger.services.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
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
    public TrainingDto getNewTraining(@PathVariable long workoutId) {
        Set<Exercise> exercises = workoutService.getWorkoutById(workoutId).getWorkout().getExercises();
        List<TrainingExerciseDto> trainingExercises = exercises.stream()
                .sorted(Comparator.comparing(Exercise::getName))
                .map(TrainingExerciseDto::new)
                .collect(Collectors.toList());
        return new TrainingDto(trainingExercises);
    }

    @GetMapping("/workouts/{workoutId}/training/{trainingId}")
    public TrainingDto getTraining(@PathVariable long workoutId, @PathVariable long trainingId) {
        return trainingService.getTrainingById(workoutId, trainingId);
    }

    @PostMapping("/workouts/{workoutId}/addTraining")
    public TrainingDto addTraining(@RequestBody TrainingDto trainingDto, @PathVariable long workoutId) {
        return trainingService.createTraining(trainingDto, workoutId);
    }

    @PutMapping("/workouts/{workoutId}/training/{trainingId}")
    public TrainingDto editTraining(@RequestBody TrainingDto trainingDto, @PathVariable long workoutId,
                                    @PathVariable long trainingId) {
        return trainingService.editTraining(trainingDto, workoutId, trainingId);
    }

    @DeleteMapping("/trainings/delete/{trainingId}")
    public void deleteTraining(@PathVariable long trainingId) {
        trainingService.deleteTraining(trainingId);
    }
}