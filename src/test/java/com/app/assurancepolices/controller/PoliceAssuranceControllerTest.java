package com.app.assurancepolices.controller;

import com.app.assurancepolices.model.dto.PoliceAssuranceCreateDto;
import com.app.assurancepolices.model.dto.PoliceAssuranceUpdateDto;
import com.app.assurancepolices.model.entity.PoliceAssurance;
import com.app.assurancepolices.service.PoliceAssuranceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.app.assurancepolices.model.Enum.Status.ACTIVE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PoliceAssuranceControllerTest {

    @Mock
    private PoliceAssuranceService policeAssuranceService;

    @InjectMocks
    private PoliceAssuranceController policeAssuranceController;

    private PoliceAssuranceCreateDto createDto;
    private PoliceAssuranceUpdateDto updateDto;
    private PoliceAssurance policeAssurance;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        createDto = new PoliceAssuranceCreateDto();
        createDto.setNom("Test Police");
        createDto.setStatus(ACTIVE);
        createDto.setDateDebutCouverture(LocalDate.now());
        createDto.setDateFinCouverture(LocalDate.now().plusDays(30));

        updateDto = new PoliceAssuranceUpdateDto();
        updateDto.setId(1);
        updateDto.setNom("Updated Test Police");
        updateDto.setStatus(ACTIVE);
        updateDto.setDateDebutCouverture(LocalDate.now());
        updateDto.setDateFinCouverture(LocalDate.now().plusDays(60));

        policeAssurance = new PoliceAssurance();
        policeAssurance.setId(1);
        policeAssurance.setNom("Test Police");
        policeAssurance.setStatus(ACTIVE);
        policeAssurance.setDateDebutCouverture(LocalDate.now());
        policeAssurance.setDateFinCouverture(LocalDate.now().plusDays(30));
    }

    @Test
    void createPolice() {
        when(policeAssuranceService.createPolice(createDto)).thenReturn(policeAssurance);

        ResponseEntity<PoliceAssurance> response = policeAssuranceController.createPolice(createDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(policeAssurance, response.getBody());
    }

    @Test
    void updatePolice_ExistingPolice() {
        when(policeAssuranceService.updatePolice(updateDto)).thenReturn(policeAssurance);

        ResponseEntity<Object> response = policeAssuranceController.updatePolice(updateDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(policeAssurance, response.getBody());
    }

    @Test
    void updatePolice_NonExistingPolice() {
        when(policeAssuranceService.updatePolice(updateDto)).thenReturn(null);

        ResponseEntity<Object> response = policeAssuranceController.updatePolice(updateDto);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(String.format("Aucune police d'assurance trouvée avec l'ID %d.", updateDto.getId()), response.getBody());
    }

    @Test
    void getPoliceById() {
        when(policeAssuranceService.getPoliceById(1)).thenReturn(Optional.of(policeAssurance));

        Optional<PoliceAssurance> result = policeAssuranceController.getPoliceById(1);

        assertEquals(Optional.of(policeAssurance), result);
    }

    @Test
    void deletePolice() {
        ResponseEntity<Void> response = policeAssuranceController.deletePolice(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(policeAssuranceService, times(1)).deletePolice(1);
    }

    @Test
    void getAllPolices() {
        List<PoliceAssurance> polices = Arrays.asList(policeAssurance);
        when(policeAssuranceService.getAllPolices()).thenReturn(polices);

        ResponseEntity<List<PoliceAssurance>> response = policeAssuranceController.getAllPolices();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(polices, response.getBody());
    }

    @Test
    void getAllPolicesByPage() {
        Pageable pageable = Pageable.unpaged();
        List<PoliceAssurance> polices = Arrays.asList(policeAssurance);
        Page<PoliceAssurance> page = new PageImpl<>(polices, pageable, polices.size());
        when(policeAssuranceService.getAllPolicesByPage(pageable)).thenReturn(page);

        Page<PoliceAssurance> result = policeAssuranceController.getAllPolicesByPage(pageable);

        assertEquals(page, result);
    }
}