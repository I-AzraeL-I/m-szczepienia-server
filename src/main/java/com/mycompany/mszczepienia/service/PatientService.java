package com.mycompany.mszczepienia.service;

import com.mycompany.mszczepienia.dto.patient.PatientDto;
import com.mycompany.mszczepienia.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;

    @PreAuthorize("#email == authentication.principal")
    public List<PatientDto> findAllByEmail(String email) {
        var patientDtoListType = new TypeToken<List<PatientDto>>() {}.getType();
        return modelMapper.map(patientRepository.findAllByUser_Email(email), patientDtoListType);
    }
}
