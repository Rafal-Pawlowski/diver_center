package com.diver.center.diver_center.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Location  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;
    private int maxDepth;
    private String difficultyLevel;

//    @ManyToMany(mappedBy = "location")
//    private List<Instructor> instructor;

    public Location(String name, String type, int maxDepth, String difficultyLevel) {
        this.name = name;
        this.type = type;
        this.maxDepth = maxDepth;
        this.difficultyLevel = difficultyLevel;
    }
}
