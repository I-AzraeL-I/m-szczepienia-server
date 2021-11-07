package com.mycompany.mszczepienia.service;

import com.mycompany.mszczepienia.dto.vaccine.VaccineDto;
import com.mycompany.mszczepienia.repository.VaccineRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VaccineService {

    private final VaccineRepository vaccineRepository;
    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<VaccineDto> findAllByDiseaseId(Long diseaseId) {
        var vaccineDtoListType = new TypeToken<List<VaccineDto>>() {}.getType();
        return modelMapper.map(vaccineRepository.findAllByDisease_Id(diseaseId), vaccineDtoListType);
    }
}
