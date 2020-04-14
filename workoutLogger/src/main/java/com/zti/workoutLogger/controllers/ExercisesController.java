package com.zti.workoutLogger.controllers;

import com.zti.workoutLogger.models.dto.ExerciseDto;
import com.zti.workoutLogger.services.ExerciseService;
import com.zti.workoutLogger.utils.auth.AuthenticatedUserGetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class ExercisesController {
    @Autowired
    private AuthenticatedUserGetter userGetter;
    @Autowired
    private ExerciseService exerciseService;

    @GetMapping("/exercises")
    public List<ExerciseDto> showCoachesList(Authentication authentication) {
        return exerciseService.getAllExercisesByUserId(userGetter.get().getId());
    }

    @PostMapping("/addExercise")
    public ExerciseDto addExercise(@RequestBody ExerciseDto exerciseDto) {
        exerciseService.createExercise(exerciseDto);
        return exerciseDto;
    }
}