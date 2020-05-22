package com.zti.workoutLogger.controllers;

import com.zti.workoutLogger.models.dto.ExerciseDto;
import com.zti.workoutLogger.models.dto.ExerciseWithResultsDto;
import com.zti.workoutLogger.services.ExerciseService;
import com.zti.workoutLogger.utils.auth.AuthenticatedUserGetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@CrossOrigin(origins = "*")
public class ExercisesController {
    @Autowired
    private AuthenticatedUserGetter userGetter;
    @Autowired
    private ExerciseService exerciseService;

    @GetMapping("/exercises")
    public List<ExerciseDto> getExercises() {
        return exerciseService.getAllExercisesByUserId(userGetter.get().getId());
    }

    @GetMapping("/exercises/{id}")
    public ExerciseWithResultsDto getExercise(@PathVariable long id) {
        return exerciseService.getExerciseById(id);
    }

    @PostMapping("/addExercise")
    public ExerciseDto addExercise(@RequestBody ExerciseDto exerciseDto) {
        return exerciseService.createExercise(exerciseDto);
    }

    @PutMapping("/exercises/edit/{id}")
    public ExerciseDto editExercise(@RequestBody ExerciseDto exerciseDto, @PathVariable long id) {
        return exerciseService.editExercise(exerciseDto, id);
    }

    @DeleteMapping("/exercises/delete/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteExercise(@PathVariable long id) {
        exerciseService.deleteExercise(id);
    }
}