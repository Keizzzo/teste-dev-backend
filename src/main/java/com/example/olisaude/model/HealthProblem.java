package com.example.olisaude.model;

import com.example.olisaude.model.enums.ProblemDegree;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class HealthProblem {
    private Long clientId;
    private String name;
    private ProblemDegree problemDegree;
}
