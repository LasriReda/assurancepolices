package com.app.assurancepolices.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.app.assurancepolices.model.dto.*;
import com.app.assurancepolices.model.entity.PoliceAssurance;
import com.app.assurancepolices.model.mapper.PoliceAssuranceMapper;
import com.app.assurancepolices.repository.PoliceAssuranceRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PoliceAssuranceService {

    private final PoliceAssuranceRepository policeAssuranceRepository;
    private final PoliceAssuranceMapper policeAssuranceMapper;

    public PoliceAssuranceService(PoliceAssuranceRepository policeAssuranceRepository, PoliceAssuranceMapper policeAssuranceMapper) {
        this.policeAssuranceRepository = policeAssuranceRepository;
        this.policeAssuranceMapper = policeAssuranceMapper;
    }

    public PoliceAssurance createPolice(PoliceAssuranceCreateDto policeDto) {
    	PoliceAssurance policeAssurance = policeAssuranceMapper.toPoliceAssurance(policeDto);
    	return policeAssuranceRepository.save(policeAssurance);
    }

    public PoliceAssurance updatePolice(PoliceAssuranceUpdateDto policeDto) {
    	Optional<PoliceAssurance> existingPolice = getPoliceById(policeDto.getId());
        if (existingPolice.isPresent()) {
        	PoliceAssurance policeAssurance = policeAssuranceMapper.toPoliceAssurance(policeDto);
        	PoliceAssurance existingPoliceAssurance = existingPolice.get();
        	policeAssurance.setDateCreation(existingPoliceAssurance.getDateCreation());
            PoliceAssurance savedPolice = policeAssuranceRepository.save(policeAssurance);
            return savedPolice;
        } else {
            return null;
        }
    }

    public Optional<PoliceAssurance> getPoliceById(Integer id) {
        return policeAssuranceRepository.findById(id);
    }

    public void deletePolice(Integer id) {
        policeAssuranceRepository.deleteById(id);
    }

    public List<PoliceAssurance> getAllPolices() {
        return policeAssuranceRepository.findAll();
    }

    public Page<PoliceAssurance> getAllPolicesByPage(Pageable pageable) {
        return policeAssuranceRepository.findAll(pageable);
    }
}
