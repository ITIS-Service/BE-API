package com.itis.service.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.itis.service.exception.ResourceNotFoundException;

import java.util.Arrays;

public enum Day {

    MONDAY(1), TUESDAY(2), WEDNESDAY(3), THURSDAY(4), FRIDAY(5), SATURDAY(6);

    private int number;

    Day(int number) {
        this.number = number;
    }

    @JsonCreator
    static Day fromValue(int value) {
        return Arrays.stream(Day.values())
                .filter(day -> day.number == value)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("День с номером " + value + " не найден"));
    }

    @JsonValue
    public int getNumber() {
        return number;
    }

}
