package com.mycompany.mszczepienia.service;

import com.mycompany.mszczepienia.dto.vaccine.DiseaseDto;
import com.mycompany.mszczepienia.repository.DiseaseRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiseaseService {

    private final DiseaseRepository diseaseRepository;
    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<DiseaseDto> findAllByNameContaining(String name) {
        var diseaseDtoListType = new TypeToken<List<DiseaseDto>>() {}.getType();
        return modelMapper.map(diseaseRepository.findAllByNameContainingIgnoreCaseOrderByName(name), diseaseDtoListType);
    }
}
