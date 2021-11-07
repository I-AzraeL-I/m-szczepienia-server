package com.mycompany.mszczepienia.service;

import com.mycompany.mszczepienia.dto.visit.FreeVisitsDto;
import com.mycompany.mszczepienia.model.Visit;
import com.mycompany.mszczepienia.model.VisitStatus;
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
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VisitService {

    public static final int VISIT_LENGTH_MIN = 5;
    public static final String USED_TIMEZONE = "Europe/Warsaw";

    private final VisitRepository visitRepository;
    private final WorkDayRepository workDayRepository;
    private final PlaceVaccineRepository placeVaccineRepository;

    @Transactional(readOnly = true)
    public FreeVisitsDto findFreeVisits(LocalDate date, Long placeId, Long vaccineId) {
        final var workDayOptional = workDayRepository.findByPlace_IdAndDayOfWeek(placeId, date.getDayOfWeek());
        if (workDayOptional.isEmpty()) {
            return new FreeVisitsDto(date, Collections.emptyList());
        }
        final var workDay = workDayOptional.get();

        var timeNow = LocalDateTime.now(ZoneId.of(USED_TIMEZONE));
        boolean isVaccineInStock = isVaccineInStock(placeId, vaccineId);
        boolean isGivenDateInPast = timeNow.isAfter(LocalDateTime.of(date, workDay.getClosingHour()));
        if (!isVaccineInStock || isGivenDateInPast) {
            return new FreeVisitsDto(date, Collections.emptyList());
        }

        final List<Visit> visits = visitRepository.findAllByPlace_IdAndDateAndTimeAfterAndVisitStatusEquals(
                placeId, date, timeNow.toLocalTime(), VisitStatus.PENDING);
        var startHour = LocalTime.from(workDay.getOpeningHour());
        var endHour = LocalTime.from(workDay.getClosingHour());
        if (timeNow.isAfter(LocalDateTime.of(date, startHour))) {
            startHour = timeNow.toLocalTime();
        }

        var parser = new RangeParser(startHour, endHour);
        visits.forEach(visit -> parser.subtractRange(visit.getTime(), visit.getTime().plusMinutes(VISIT_LENGTH_MIN)));

        List<LocalTime> freeVisits = parser.parseToStartTimeList(VISIT_LENGTH_MIN, ChronoUnit.MINUTES);
        return new FreeVisitsDto(date, freeVisits);
    }

    private boolean isVaccineInStock(Long placeId, Long vaccineId) {
        return placeVaccineRepository.existsByPlace_IdAndVaccine_IdAndQuantityIsGreaterThan(placeId, vaccineId, 0);
    }
}
