package com.diver.center.diver_center.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "instructors")
public class Instructor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "instructor_id")
    private Long id;
    private String name;

    @OneToOne
    @JoinColumn(name = "licence_id")
    private Licence licence;

    private int age;
    private List<String> trainingType;

    public Instructor(String name, Licence licenceNumber, int age, List<String> trainingType) {
        this.name = name;
        this.licence = licenceNumber;
        this.age = age;
        this.trainingType = trainingType;
    }

}




