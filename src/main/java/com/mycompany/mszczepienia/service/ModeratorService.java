package com.mycompany.mszczepienia.service;

import com.mycompany.mszczepienia.dto.visit.VisitWithVaccineAndPatientDto;
import com.mycompany.mszczepienia.exception.VisitStatusException;
import com.mycompany.mszczepienia.model.VisitStatus;
import com.mycompany.mszczepienia.repository.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ModeratorService {

    private final VisitRepository visitRepository;
    private final ModelMapper modelMapper;

    @PreAuthorize("@moderatorAccess.isModerator()")
    @Transactional(readOnly = true)
    public List<VisitWithVaccineAndPatientDto> findAllPendingVisits(Long moderatorId, LocalDate date) {
        var visitDtoListType = new TypeToken<List<VisitWithVaccineAndPatientDto>>() {}.getType();
        var sort = Sort.by("date").descending().and(Sort.by("time")).descending();
        return modelMapper.map(visitRepository.findAllByPlace_Moderator_IdAndDateAndVisitStatusEquals(moderatorId, date, VisitStatus.PENDING, sort), visitDtoListType);
    }

    @PreAuthorize("@moderatorAccess.isModerator()")
    @Transactional
    public void acceptVisit(Long visitId) {
        visitRepository.findById(visitId)
                .filter(visit -> visit.getVisitStatus().equals(VisitStatus.PENDING))
                .ifPresentOrElse(
                        visit -> visit.setVisitStatus(VisitStatus.FINISHED),
                        () -> { throw new VisitStatusException(visitId.toString(), "Visit cannot be cancelled"); });
    }
}
