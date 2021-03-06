package com.zti.workoutLogger.models.dto;

import com.zti.workoutLogger.models.ModelSet;
import com.zti.workoutLogger.models.Training;
import com.zti.workoutLogger.utils.DateToStringConverter;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ResultDto {
    private String name;
    private String date;
    private String sets;
    private float volume;

    public ResultDto() {
    }

    public ResultDto(Training training, List<ModelSet> modelSets) {
        this.name = training.getWorkout().getName();
        this.date = DateToStringConverter.convert(training.getDate());
        this.sets = modelSets.stream()
                .map(ModelSet::print)
                .collect(Collectors.joining(", "));
        this.volume = modelSets.stream()
                .map(ModelSet::getVolume)
                .reduce(0f, Float::sum);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSets() {
        return sets;
    }

    public void setSets(String sets) {
        this.sets = sets;
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public NameDate getNameDate() {
        return new NameDate(name, date);
    }

    public static class NameDate {
        private final String name;
        private final String date;

        public NameDate(String name, String date) {
            this.name = name;
            this.date = date;
        }

        public String getName() {
            return name;
        }

        public String getDate() {
            return date;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            NameDate that = (NameDate) o;
            return Objects.equals(name, that.name) &&
                    Objects.equals(date, that.date);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, date);
        }
    }
}