package com.diver.center.diver_center.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import java.util.List;
import java.util.Set;

import static org.hibernate.annotations.CascadeType.*;
import static org.hibernate.annotations.CascadeType.REFRESH;

//@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;
    private int maxDepth;
    private String difficultyLevel;

    @ManyToMany
    @JsonIgnore
    @Cascade({PERSIST, MERGE})
    @JoinTable(name = "location_instructors",
            joinColumns = @JoinColumn(name = "locations_id"),
            inverseJoinColumns = @JoinColumn(name = "instructors_instructor_id"))
    private Set<Instructor> instructorSet;


    public void addInstructorSet(Instructor instructor){
        this.instructorSet.add(instructor);
    }

    public void removeInstructorSet(Instructor instructor){
        this.instructorSet.remove(instructor);
    }

    public Location(String name, String type, int maxDepth, String difficultyLevel) {
        this.name = name;
        this.type = type;
        this.maxDepth = maxDepth;
        this.difficultyLevel = difficultyLevel;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    public void setMaxDepth(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    public String getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public Set<Instructor> getInstructorSet() {
        return instructorSet;
    }

    public void setInstructorSet(Set<Instructor> instructorSet) {
        this.instructorSet = instructorSet;
    }
}
