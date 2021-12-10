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

    @PreAuthorize("@moderatorAccess.isModerator()")
    public List<VisitWithVaccineAndPatientDto> findAllPendingVisits(Long moderatorId, LocalDate date) {
        var visitDtoListType = new TypeToken<List<VisitWithVaccineAndPatientDto>>() {}.getType();
        return modelMapper.map(visitRepository.findAllByPlace_Moderator_IdAndDateAndVisitStatusEquals(moderatorId, date, VisitStatus.PENDING), visitDtoListType);
    }

    @PreAuthorize("@moderatorAccess.isModerator()")
    public void acceptVisit(Long visitId) {
        visitRepository.findById(visitId)
                .filter(visit -> visit.getVisitStatus().equals(VisitStatus.PENDING))
                .ifPresentOrElse(
                        visit -> visit.setVisitStatus(VisitStatus.FINISHED),
                        () -> {} /*todo wait for PR to throw*/);
    }
}
