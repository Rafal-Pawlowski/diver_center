package com.diver.center.diver_center.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Trainee {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private int age;
    private String gender;
    private String language;

    public Trainee(String name, int age, String gender, String language) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.language = language;
    }
}
