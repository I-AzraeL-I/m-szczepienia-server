package com.mycompany.mszczepienia.service;

import com.mycompany.mszczepienia.dto.appointment.FreeVisitsDto;
import com.mycompany.mszczepienia.model.PlaceVaccine;
import com.mycompany.mszczepienia.model.Visit;
import com.mycompany.mszczepienia.model.WorkDay;
import com.mycompany.mszczepienia.repository.PlaceVaccineRepository;
import com.mycompany.mszczepienia.repository.VisitRepository;
import com.mycompany.mszczepienia.repository.WorkDayRepository;
import com.mycompany.mszczepienia.util.RangeParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VisitService {

    private static final int VISIT_LENGTH_MIN = 5;

    private final VisitRepository visitRepository;
    private final WorkDayRepository workDayRepository;
    private final PlaceVaccineRepository placeVaccineRepository;

    public FreeVisitsDto findFreeVisits(LocalDate date, Long placeId, Long vaccineId) {
        Optional<WorkDay> workDayOptional = workDayRepository.findByPlace_IdAndDayOfWeek(placeId, date.getDayOfWeek());
        Optional<PlaceVaccine> placeVaccineOptional = placeVaccineRepository.findByPlace_IdAndVaccine_Id(placeId, vaccineId);
        if (workDayOptional.isEmpty() || placeVaccineOptional.isEmpty() || placeVaccineOptional.get().getQuantity() <= 0)
            return new FreeVisitsDto(date, Collections.emptyList());

        WorkDay workDay = workDayOptional.get();
        List<Visit> visits = visitRepository.findAllByPlace_IdAndDate(placeId, date);
        var parser = new RangeParser(workDay.getOpeningHour(), workDay.getClosingHour());
        visits.forEach(visit -> parser.subtractRange(visit.getTime(), visit.getTime().plusMinutes(VISIT_LENGTH_MIN)));
        var freeVisitHours = parser.toLowerEndpointList(VISIT_LENGTH_MIN, ChronoUnit.MINUTES);

        return new FreeVisitsDto(date, freeVisitHours);
    }
}
