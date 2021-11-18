package com.mycompany.mszczepienia.service;

import com.mycompany.mszczepienia.dto.visit.CreateVisitDto;
import com.mycompany.mszczepienia.dto.visit.FreeVisitsDto;
import com.mycompany.mszczepienia.dto.visit.VisitDto;
import com.mycompany.mszczepienia.exception.InvalidVisitException;
import com.mycompany.mszczepienia.exception.UserNotFoundException;
import com.mycompany.mszczepienia.exception.VaccineOutOfStockException;
import com.mycompany.mszczepienia.model.Visit;
import com.mycompany.mszczepienia.model.VisitStatus;
import com.mycompany.mszczepienia.repository.*;
import com.mycompany.mszczepienia.util.RangeParser;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
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

    @Value("${app.visitLengthMin}")
    private int visitLengthMin;

    @Value("${app.usedTimezone}")
    private String usedTimezone;

    private final VisitRepository visitRepository;
    private final WorkDayRepository workDayRepository;
    private final PlaceVaccineRepository placeVaccineRepository;
    private final PlaceRepository placeRepository;
    private final VaccineRepository vaccineRepository;
    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public FreeVisitsDto findFreeVisits(LocalDate date, Long placeId, Long vaccineId) {
        final var workDayOptional = workDayRepository.findByPlace_IdAndDayOfWeek(placeId, date.getDayOfWeek());
        if (workDayOptional.isEmpty()) {
            return new FreeVisitsDto(date, Collections.emptyList());
        }
        final var workDay = workDayOptional.get();

        var timeNow = LocalDateTime.now(ZoneId.of(usedTimezone));
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
            startHour = adjustStartHour(timeNow.toLocalTime());
        }

        var parser = new RangeParser(startHour, endHour);
        visits.forEach(visit -> parser.subtractRange(visit.getTime(), visit.getTime().plusMinutes(visitLengthMin)));

        List<LocalTime> freeVisits = parser.parseToStartTimeList(visitLengthMin, ChronoUnit.MINUTES);
        return new FreeVisitsDto(date, freeVisits);
    }

    @Transactional
    public VisitDto createVisit(CreateVisitDto visitDto) {
        if (!isVisitInFuture(visitDto) || isVisitAlreadyTaken(visitDto)) {
            throw new InvalidVisitException(LocalDateTime.of(visitDto.getDate(), visitDto.getTime()).toString(), "Visit request is invalid");
        }

        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var patient = patientRepository.findByIdAndUser_Email(visitDto.getPatientId(), email)
                .orElseThrow(() -> new UserNotFoundException(visitDto.getPatientId().toString(), "Patient not found"));
        if (placeVaccineRepository.decrementQuantity(visitDto.getPlaceId(), visitDto.getVaccineId()) != 1) {
            throw new VaccineOutOfStockException(visitDto.getVaccineId().toString(), "Vaccine is out of stock");
        }

        var visit = new Visit();
        visit.setVisitStatus(VisitStatus.PENDING);
        visit.setDate(visitDto.getDate());
        visit.setTime(visitDto.getTime());
        visit.setPlace(placeRepository.getById(visitDto.getPlaceId()));
        visit.setVaccine(vaccineRepository.getById(visitDto.getVaccineId()));

        patient.addVisit(visit);
        return modelMapper.map(visit, VisitDto.class);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void updateMissedVisitsStatus() {
        List<Visit> visits = visitRepository.findAllByDateBeforeAndVisitStatusEquals(LocalDate.now(ZoneId.of(usedTimezone)), VisitStatus.PENDING);
        visits.forEach(visit -> {
            visit.setVisitStatus(VisitStatus.MISSED);
            placeVaccineRepository.incrementQuantity(visit.getPlace().getId(), visit.getVaccine().getId());
        });
    }

    private boolean isVaccineInStock(Long placeId, Long vaccineId) {
        return placeVaccineRepository.existsByPlace_IdAndVaccine_IdAndQuantityIsGreaterThan(placeId, vaccineId, 0);
    }

    private boolean isVisitAlreadyTaken(CreateVisitDto createVisitDto) {
        return visitRepository.existsByDateAndTimeAndVisitStatus(createVisitDto.getDate(), createVisitDto.getTime(), VisitStatus.PENDING);
    }

    private boolean isVisitInFuture(CreateVisitDto createVisitDto) {
        return LocalDateTime.of(createVisitDto.getDate(), createVisitDto.getTime()).isAfter(LocalDateTime.now(ZoneId.of(usedTimezone)));
    }

    private LocalTime adjustStartHour(LocalTime time) {
        int minutes = (int) (Math.ceil((float) time.getMinute() / visitLengthMin) * visitLengthMin);
        return time.truncatedTo(ChronoUnit.MINUTES).withMinute(minutes);
    }
}
