package com.app.assurancepolices.service;

import com.app.assurancepolices.model.dto.PoliceAssuranceCreateDto;
import com.app.assurancepolices.model.dto.PoliceAssuranceUpdateDto;
import com.app.assurancepolices.model.entity.PoliceAssurance;
import com.app.assurancepolices.repository.PoliceAssuranceRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.app.assurancepolices.model.Enum.Status.ACTIVE;
import static com.app.assurancepolices.model.Enum.Status.INACTIVE;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class PoliceAssuranceServiceTest {

    @Autowired
    private PoliceAssuranceService policeAssuranceService;

    @Autowired
    private PoliceAssuranceRepository policeAssuranceRepository;

    private PoliceAssuranceCreateDto createDto;
    private PoliceAssuranceUpdateDto updateDto;
    private PoliceAssurance policeAssurance;

    @BeforeEach
    void setUp() {
        createDto = new PoliceAssuranceCreateDto();
        createDto.setNom("Test Police Assurance 1");
        createDto.setStatus(ACTIVE);
        createDto.setDateDebutCouverture(LocalDate.now());
        createDto.setDateFinCouverture(LocalDate.now().plusDays(30));

        policeAssurance = new PoliceAssurance();
        policeAssurance.setNom("Test Police Assurance 2");
        policeAssurance.setStatus(ACTIVE);
        policeAssurance.setDateDebutCouverture(LocalDate.now());
        policeAssurance.setDateFinCouverture(LocalDate.now().plusDays(30));
        policeAssuranceRepository.save(policeAssurance);

        updateDto = new PoliceAssuranceUpdateDto();
        updateDto.setId(policeAssurance.getId());
        updateDto.setNom("Updated Test Police");
        updateDto.setStatus(INACTIVE);
        updateDto.setDateDebutCouverture(LocalDate.now().plusDays(1));
        updateDto.setDateFinCouverture(LocalDate.now().plusDays(60));
    }
    
    @AfterEach
    void cleanSavedData() {
    	policeAssuranceRepository.deleteAll();
    }

    @Test
    void createPolice() {
        PoliceAssurance result = policeAssuranceService.createPolice(createDto);

        assertNotNull(result.getId());
        assertEquals(createDto.getNom(), result.getNom());
        assertEquals(createDto.getStatus(), result.getStatus());
        assertEquals(createDto.getDateDebutCouverture(), result.getDateDebutCouverture());
        assertEquals(createDto.getDateFinCouverture(), result.getDateFinCouverture());
    }

    @Test
    void updatePolice_ExistingPolice() {
        PoliceAssurance result = policeAssuranceService.updatePolice(updateDto);

        assertNotNull(result);
        assertEquals(updateDto.getId(), result.getId());
        assertEquals(updateDto.getNom(), result.getNom());
        assertEquals(updateDto.getStatus(), result.getStatus());
        assertEquals(updateDto.getDateDebutCouverture(), result.getDateDebutCouverture());
        assertEquals(updateDto.getDateFinCouverture(), result.getDateFinCouverture());
    }

    @Test
    void updatePolice_NonExistingPolice() {
        updateDto.setId(999999);
        PoliceAssurance result = policeAssuranceService.updatePolice(updateDto);

        assertNull(result);
    }

    @Test
    void getPoliceById() {
        Optional<PoliceAssurance> result = policeAssuranceService.getPoliceById(policeAssurance.getId());

        assertTrue(result.isPresent());
        assertEquals(policeAssurance.getId(), result.get().getId());
        assertEquals(policeAssurance.getNom(), result.get().getNom());
    }

    @Test
    void deletePolice() {
        policeAssuranceService.deletePolice(policeAssurance.getId());
        Optional<PoliceAssurance> deletedPolice = policeAssuranceRepository.findById(policeAssurance.getId());

        assertTrue(deletedPolice.isEmpty());
    }

    @Test
    void getAllPolices() {
        List<PoliceAssurance> policesA = Arrays.asList(policeAssurance);
        List<PoliceAssurance> result = policeAssuranceService.getAllPolices();
        assertEquals(policesA, result);
    }

    @Test
    void getAllPolicesByPage() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<PoliceAssurance> page = policeAssuranceService.getAllPolicesByPage(pageable);

        assertFalse(page.isEmpty());
        assertTrue(page.getContent().stream().anyMatch(p -> p.getId().equals(policeAssurance.getId())));
    }
}