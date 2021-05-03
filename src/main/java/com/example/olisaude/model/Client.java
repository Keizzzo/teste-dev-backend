package com.example.olisaude.model;

import com.example.olisaude.model.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Client {

    private String name;
    private LocalDate birthday;
    private Gender gender;
    private List<HealthProblem> healthProblemList;

}
