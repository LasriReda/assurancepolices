package com.app.assurancepolices.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.assurancepolices.model.entity.PoliceAssurance;

@Repository
public interface PoliceAssuranceRepository extends JpaRepository<PoliceAssurance, Integer> {
	
}