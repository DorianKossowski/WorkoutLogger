package com.zti.workoutLogger.controllers;

import com.zti.workoutLogger.models.Exercise;
import com.zti.workoutLogger.models.dto.ExerciseDto;
import com.zti.workoutLogger.repositories.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
public class ExercisesController {
    @Autowired
    private ExerciseRepository exerciseRepository;

    @GetMapping("/exercises")
    public List<ExerciseDto> showCoachesList() {
        return exerciseRepository.findAll().stream()
                .map(ExerciseDto::new)
                .collect(Collectors.toList());
    }

    @PostMapping("/addExercise")
    public ExerciseDto addExercise() {
        return new ExerciseDto(new Exercise());
    }
}