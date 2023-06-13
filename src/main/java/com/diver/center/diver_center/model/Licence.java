package com.diver.center.diver_center.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Licence {

    @Id
    @GeneratedValue
    private Long id;
    private int licenceNumber;
    private Date dateOfRelease;
    private String federation;
    private String level;

    public Licence(int licenceNumber, Date dateOfRelease, String federation, String level) {
        this.licenceNumber = licenceNumber;
        this.dateOfRelease = dateOfRelease;
        this.federation = federation;
        this.level = level;
    }
}
