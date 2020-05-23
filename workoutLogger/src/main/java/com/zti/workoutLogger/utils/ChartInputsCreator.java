package com.zti.workoutLogger.utils;

import com.zti.workoutLogger.models.dto.ChartInputDto;
import com.zti.workoutLogger.models.dto.ResultDto;
import com.zti.workoutLogger.models.dto.TrainingDto;
import com.zti.workoutLogger.models.dto.TrainingExerciseDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

public class ChartInputsCreator {

    public static List<ChartInputDto> create(Set<ResultDto> results) {
        List<ChartInputDto> charInputs = new ArrayList<>();
        Map<ResultDto.NameDate, Double> resultsByNameDate = results.stream()
                .collect(groupingBy(ResultDto::getNameDate, summingDouble(ResultDto::getVolume)));
        Map<String, Map<String, String>> resultsByDate = resultsByNameDate.entrySet().stream()
                .collect(groupingBy(entry -> entry.getKey().getDate(), Collectors.toMap(o -> o.getKey().getName(),
                        entry -> entry.getValue().toString())
                ));
        resultsByDate.forEach((date, trainingVolume) -> charInputs.add(new ChartInputDto(date, trainingVolume)));
        return charInputs;
    }

    public static List<ChartInputDto> create(List<TrainingDto> trainings) {
        List<ChartInputDto> charInputs = new ArrayList<>();
        Map<String, List<Map<String, Float>>> exerciseNameVolumeByDate =
                trainings.stream().collect(exerciseNameVolumeByDateCollector());
        exerciseNameVolumeByDate.forEach((date, exerciseNameVolumes) -> {
            Map<String, Float> collect = exerciseNameVolumes.stream()
                    .flatMap(stringFloatMap -> stringFloatMap.entrySet().stream())
                    .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, Float::sum));
            Map<String, String> collect1 = collect.entrySet().stream().collect(toMap(Map.Entry::getKey,
                    entry -> entry.getValue().toString()));
            charInputs.add(new ChartInputDto(date, collect1));
        });
        return charInputs;
    }

    private static Collector<TrainingDto, ?, Map<String, List<Map<String, Float>>>> exerciseNameVolumeByDateCollector() {
        return groupingBy(TrainingDto::getDate, mapping(trainingDto -> trainingDto.getExercises().stream()
                .collect(toMap(TrainingExerciseDto::getName, TrainingExerciseDto::getVolume)), toList()));
    }
}