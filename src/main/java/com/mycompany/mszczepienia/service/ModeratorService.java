package com.mycompany.mszczepienia.service;

import com.mycompany.mszczepienia.dto.visit.VisitWithVaccineAndPatientDto;
import com.mycompany.mszczepienia.model.VisitStatus;
import com.mycompany.mszczepienia.repository.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ModeratorService {

    private final VisitRepository visitRepository;
    private final ModelMapper modelMapper;

    @PreAuthorize("@moderatorAccess.isModerator(#placeId)")
    public List<VisitWithVaccineAndPatientDto> findAllPendingVisits(Long placeId, LocalDate date) {
        var visitDtoListType = new TypeToken<List<VisitWithVaccineAndPatientDto>>() {}.getType();
        return modelMapper.map(visitRepository.findAllByPlace_IdAndDateAndVisitStatusEquals(placeId, date, VisitStatus.PENDING), visitDtoListType);
    }
}
