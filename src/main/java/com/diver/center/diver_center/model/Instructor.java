package com.diver.center.diver_center.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.util.List;

import static org.hibernate.annotations.CascadeType.DETACH;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "instructors")
public class Instructor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "instructor_id")
    private Long id;
    private String name;

    @OneToOne(cascade = {jakarta.persistence.CascadeType.DETACH, jakarta.persistence.CascadeType.REMOVE})
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




