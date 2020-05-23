package com.zti.workoutLogger.utils;

import com.zti.workoutLogger.models.dto.ChartInputDto;
import com.zti.workoutLogger.models.dto.ResultDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingDouble;

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
}