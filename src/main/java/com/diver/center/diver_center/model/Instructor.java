package com.diver.center.diver_center.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.util.List;
import java.util.Set;

import static org.hibernate.annotations.CascadeType.*;

//@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "instructors")
public class Instructor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "instructor_id")
    private Long id;
    private String name;

    @OneToOne(cascade = jakarta.persistence.CascadeType.DETACH) // dodac pozostałe CascadeTypy oprócz delete
    @JoinColumn(name = "licence_id")
    private Licence licence;

    private int age;
    private List<String> trainingType;

    @OneToMany(mappedBy = "instructor")
    @Cascade({DETACH, PERSIST, MERGE})
    List<Trainee> trainees;

    @ManyToMany(mappedBy = "instructorSet")
    @Cascade({ PERSIST, MERGE})
    private Set<Location> locations;



    public Instructor(String name, Licence licenceNumber, int age, List<String> trainingType) {
        this.name = name;
        this.licence = licenceNumber;
        this.age = age;
        this.trainingType = trainingType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Licence getLicence() {
        return licence;
    }

    public void setLicence(Licence licence) {
        this.licence = licence;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<String> getTrainingType() {
        return trainingType;
    }

    public void setTrainingType(List<String> trainingType) {
        this.trainingType = trainingType;
    }

    public List<Trainee> getTrainees() {
        return trainees;
    }

    public void setTrainees(List<Trainee> trainees) {
        this.trainees = trainees;
    }

    public Set<Location> getLocations() {
        return locations;
    }

    public void setLocations(Set<Location> locations) {
        this.locations = locations;
    }
}




