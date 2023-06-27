package com.diver.center.diver_center.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Licence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private int licenceNumber;
    private LocalDate dateOfRelease;
    private String federation;
    private String level;

    @OneToOne(mappedBy = "licence")
    @Cascade(org.hibernate.annotations.CascadeType.PERSIST)
    @JsonIgnore
    private Instructor instructor;

    public Licence(int licenceNumber, LocalDate dateOfRelease, String federation, String level, Object o) {
        this.licenceNumber = licenceNumber;
        this.dateOfRelease = dateOfRelease;
        this.federation = federation;
        this.level = level;
    }
}
