package com.diver.center.diver_center.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Licence {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private int licenceNumber;
    private LocalDate dateOfRelease;
    private String federation;
    private String level;

    public Licence(int licenceNumber, LocalDate dateOfRelease, String federation, String level) {
        this.licenceNumber = licenceNumber;
        this.dateOfRelease = dateOfRelease;
        this.federation = federation;
        this.level = level;
    }
}
