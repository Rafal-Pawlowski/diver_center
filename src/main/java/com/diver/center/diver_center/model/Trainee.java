package com.diver.center.diver_center.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;


import static jakarta.persistence.CascadeType.*;
import static org.hibernate.annotations.CascadeType.*;
import static org.hibernate.annotations.CascadeType.DETACH;
import static org.hibernate.annotations.CascadeType.MERGE;
import static org.hibernate.annotations.CascadeType.PERSIST;

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

    @ManyToOne
    @JsonIgnore
    @Cascade({DETACH, MERGE, PERSIST})
    @JoinColumn(name = "instructorId")
    private Instructor instructor;

    public Trainee(String name, int age, String gender, String language) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.language = language;
    }
}
