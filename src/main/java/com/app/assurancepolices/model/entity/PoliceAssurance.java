package com.app.assurancepolices.model.entity;

import lombok.Data;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;

import com.app.assurancepolices.model.Enum.Status;

@Entity
@Data
public class PoliceAssurance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "NOM", nullable = false)
    private String nom;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false)
    private Status status;

    @Column(name = "DATE_DEBUT_COUVERTURE", nullable = false)
    private LocalDate dateDebutCouverture;

    @Column(name = "DATE_FIN_COUVERTURE", nullable = false)
    private LocalDate dateFinCouverture;

    @Column(name = "DATE_CREATION", nullable = false, updatable = false)
    private Date dateCreation;

    @Column(name = "DATE_MISE_A_JOUR", nullable = false)
    private Date dateMiseAJour;

    @PrePersist
    protected void onCreate() {
        dateCreation = new Date();
        dateMiseAJour = dateCreation;
    }

    @PreUpdate
    protected void onUpdate() {
        dateMiseAJour = new Date();
    }
    
 
}