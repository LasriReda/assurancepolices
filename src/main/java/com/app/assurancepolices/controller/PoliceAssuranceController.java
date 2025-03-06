package com.app.assurancepolices.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.app.assurancepolices.model.dto.*;
import com.app.assurancepolices.model.entity.PoliceAssurance;
import com.app.assurancepolices.service.PoliceAssuranceService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/polices")
@Tag(name = "Polices d'Assurance", description = "API pour gérer les polices d'assurance")
public class PoliceAssuranceController {

    private final PoliceAssuranceService policeAssuranceService;

    public PoliceAssuranceController(PoliceAssuranceService policeAssuranceService) {
        this.policeAssuranceService = policeAssuranceService;
    }

    @PostMapping("/create")
    public ResponseEntity<PoliceAssurance> createPolice(@Valid @RequestBody PoliceAssuranceCreateDto policeDto) {
    	PoliceAssurance p= policeAssuranceService.createPolice(policeDto);
    	return new ResponseEntity<>(p, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Object> updatePolice(@Valid @RequestBody PoliceAssuranceUpdateDto policeDto) {
    	PoliceAssurance p= policeAssuranceService.updatePolice(policeDto);
        if (p != null) {
            return ResponseEntity.ok(p);
        } else {
        	String errorMessage = String.format("Aucune police d'assurance trouvée avec l'ID %d.", policeDto.getId());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }

    @GetMapping("/{id}")
    public Optional<PoliceAssurance> getPoliceById(@PathVariable Integer id) {
        return policeAssuranceService.getPoliceById(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePolice(@PathVariable Integer id) {
    	policeAssuranceService.deletePolice(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<PoliceAssurance>> getAllPolices() {
    	List<PoliceAssurance> lp= policeAssuranceService.getAllPolices();
    	return ResponseEntity.ok(lp);
    }

    @GetMapping("/byPage")
    public Page<PoliceAssurance> getAllPolicesByPage(Pageable pageable) {
        return policeAssuranceService.getAllPolicesByPage(pageable);
    }
}