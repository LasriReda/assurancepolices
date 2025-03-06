package com.app.assurancepolices.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import com.app.assurancepolices.model.dto.*;
import com.app.assurancepolices.model.entity.PoliceAssurance;

@Mapper(componentModel = "spring")
@Component
public interface PoliceAssuranceMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateCreation", ignore = true)
    @Mapping(target = "dateMiseAJour", ignore = true)
    PoliceAssurance toPoliceAssurance(PoliceAssuranceCreateDto dto);

    @Mapping(target = "dateCreation", ignore = true)
    @Mapping(target = "dateMiseAJour", ignore = true)
    PoliceAssurance toPoliceAssurance(PoliceAssuranceUpdateDto dto);
}
