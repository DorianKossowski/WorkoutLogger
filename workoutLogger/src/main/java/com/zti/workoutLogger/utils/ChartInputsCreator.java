package com.zti.workoutLogger.utils;

import com.zti.workoutLogger.models.dto.ChartInputDto;
import com.zti.workoutLogger.models.dto.ResultDto;
import com.zti.workoutLogger.models.dto.TrainingDto;
import com.zti.workoutLogger.models.dto.TrainingExerciseDto;

import java.time.LocalDate;
import java.util.*;
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

        List<Map.Entry<String, Map<String, String>>> resultsBySortedDate = resultsByDate.entrySet().stream()
                .sorted(getStringDateComparator())
                .collect(toList());
        resultsBySortedDate.forEach(stringMapEntry -> charInputs.add(new ChartInputDto(stringMapEntry.getKey(),
                stringMapEntry.getValue())));
        return charInputs;
    }

    private static Comparator<Map.Entry<String, ?>> getStringDateComparator() {
        return Comparator.comparing(stringMapEntry -> LocalDate.parse(stringMapEntry.getKey(),
                DateToStringConverter.FORMATTER));
    }

    public static List<ChartInputDto> create(List<TrainingDto> trainings) {
        List<ChartInputDto> charInputs = new ArrayList<>();
        Map<String, List<Map<String, Float>>> exerciseNameVolumeByDate = trainings.stream()
                .collect(exerciseNameVolumeByDateCollector());

        List<Map.Entry<String, List<Map<String, Float>>>> exerciseNameVolumeBySortedDate =
                exerciseNameVolumeByDate.entrySet().stream()
                        .sorted(getStringDateComparator())
                        .collect(toList());

        exerciseNameVolumeBySortedDate.forEach(stringListEntry -> {
            Map<String, Float> volumeByExercise = stringListEntry.getValue().stream()
                    .flatMap(stringFloatMap -> stringFloatMap.entrySet().stream())
                    .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, Float::sum));
            Map<String, String> stringVolumeByExercise = volumeByExercise.entrySet().stream()
                    .collect(toMap(Map.Entry::getKey, entry -> entry.getValue().toString()));
            charInputs.add(new ChartInputDto(stringListEntry.getKey(), stringVolumeByExercise));
        });
        return charInputs;
    }

    private static Collector<TrainingDto, ?, Map<String, List<Map<String, Float>>>> exerciseNameVolumeByDateCollector() {
        return groupingBy(TrainingDto::getDate, mapping(trainingDto -> trainingDto.getExercises().stream()
                .collect(toMap(TrainingExerciseDto::getName, TrainingExerciseDto::getVolume)), toList()));
    }
}