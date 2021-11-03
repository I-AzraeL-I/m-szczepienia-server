package com.mycompany.mszczepienia.service;

import com.mycompany.mszczepienia.dto.appointment.FreeVisitsDto;
import com.mycompany.mszczepienia.model.PlaceVaccine;
import com.mycompany.mszczepienia.model.Visit;
import com.mycompany.mszczepienia.model.VisitStatus;
import com.mycompany.mszczepienia.model.WorkDay;
import com.mycompany.mszczepienia.repository.PlaceVaccineRepository;
import com.mycompany.mszczepienia.repository.VisitRepository;
import com.mycompany.mszczepienia.repository.WorkDayRepository;
import com.mycompany.mszczepienia.util.RangeParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    @Transactional(readOnly = true)
    public FreeVisitsDto findFreeVisits(LocalDate date, Long placeId, Long vaccineId) {
        LocalDateTime now = LocalDateTime.now();
        final Optional<WorkDay> workDayOptional = workDayRepository.findByPlace_IdAndDayOfWeek(placeId, date.getDayOfWeek());
        final Optional<PlaceVaccine> placeVaccineOptional = placeVaccineRepository.findByPlace_IdAndVaccine_Id(placeId, vaccineId);
        if (workDayOptional.isEmpty()
                || placeVaccineOptional.isEmpty()
                || placeVaccineOptional.get().getQuantity() <= 0
                || now.isAfter(LocalDateTime.of(date, workDayOptional.get().getClosingHour())))
            return new FreeVisitsDto(date, Collections.emptyList());

        final WorkDay workDay = workDayOptional.get();
        LocalTime openingHour = LocalTime.from(workDay.getOpeningHour());
        LocalTime closingHour = LocalTime.from(workDay.getClosingHour());
        if (now.isAfter(LocalDateTime.of(date, openingHour))) {
            openingHour = now.toLocalTime();
        }

        final List<Visit> visits = visitRepository.findAllByPlace_IdAndDateAndTimeAfterAndVisitStatusEquals(
                placeId,
                date,
                LocalTime.now(),
                VisitStatus.PENDING);
        var parser = new RangeParser(openingHour, closingHour);
        visits.forEach(visit -> parser.subtractRange(visit.getTime(), visit.getTime().plusMinutes(VISIT_LENGTH_MIN)));
        var freeVisitHours = parser.toLowerEndpointList(VISIT_LENGTH_MIN, ChronoUnit.MINUTES);

        return new FreeVisitsDto(date, freeVisitHours);
    }
}