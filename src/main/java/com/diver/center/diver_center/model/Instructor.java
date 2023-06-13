package com.diver.center.diver_center.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.logging.Level;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Instructor {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private int licenceNumber;
    private int age;
    private List<String> trainingType;

    public Instructor(String name, int licenceNumber, int age, List<String> trainingType) {
        this.name = name;
        this.licenceNumber = licenceNumber;
        this.age = age;
        this.trainingType = trainingType;
    }
}




