package com.example.olisaude.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ProblemDegree {

    UM("1"),
    DOIS("2");

    private String value;

    ProblemDegree(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static ProblemDegree fromValue(String text) {
        for (ProblemDegree b : ProblemDegree.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }
}
