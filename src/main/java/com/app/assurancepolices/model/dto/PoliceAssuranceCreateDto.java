package com.app.assurancepolices.model.dto;


import lombok.Data;

import java.time.LocalDate;
import java.util.Date;


import com.app.assurancepolices.model.Enum.Status;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


@Data
public class PoliceAssuranceCreateDto {

	@NotBlank(message = "Le nom ne peut pas être vide")
	@NotNull(message = "Le nom ne peut pas être null")
    private String nom;

	@NotNull(message = "Le statut ne peut pas être vide")
    private Status status;

	@NotNull(message = "La date de début ne peut pas être nulle")
    @JsonProperty("dateDebutCouverture")
    private LocalDate dateDebutCouverture;

	@NotNull(message = "La date de fin de couverture ne peut pas être nulle")
    private LocalDate dateFinCouverture;

 
}