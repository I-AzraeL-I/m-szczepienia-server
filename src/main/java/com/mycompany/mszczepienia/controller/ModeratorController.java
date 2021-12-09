package com.mycompany.mszczepienia.controller;

import com.mycompany.mszczepienia.dto.visit.VisitWithVaccineAndPatientDto;
import com.mycompany.mszczepienia.service.ModeratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/moderator")
@RequiredArgsConstructor
public class ModeratorController {

    private final ModeratorService moderatorService;

    @GetMapping("/visits")
    public ResponseEntity<List<VisitWithVaccineAndPatientDto>> getAllPendingVisits(@RequestParam Long placeId,
                                                                                   @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate date) {
        return ResponseEntity.ok(moderatorService.findAllPendingVisits(placeId, date));
    }
}
