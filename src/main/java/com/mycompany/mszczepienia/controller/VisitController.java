package com.mycompany.mszczepienia.controller;

import com.mycompany.mszczepienia.dto.visit.CreateVisitDto;
import com.mycompany.mszczepienia.dto.visit.FreeVisitsDto;
import com.mycompany.mszczepienia.dto.visit.VisitDto;
import com.mycompany.mszczepienia.model.Visit;
import com.mycompany.mszczepienia.service.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/visit")
@RequiredArgsConstructor
public class VisitController {

    private final VisitService visitService;

    @GetMapping("/find")
    public ResponseEntity<FreeVisitsDto> getFreeVisits(@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate date,
                                                       @RequestParam Long placeId,
                                                       @RequestParam Long vaccineId) {
        return ResponseEntity.ok(visitService.findFreeVisits(date, placeId, vaccineId));
    }

    @PostMapping("")
    public ResponseEntity<VisitDto> createVisit(@Valid @RequestBody CreateVisitDto createVisitDto) {
        return ResponseEntity.ok(visitService.createVisit(createVisitDto));
    }

    @PutMapping("/cancel")
    public ResponseEntity<VisitDto> cancelVisit(@Valid @RequestParam Long visiId){
        return ResponseEntity.ok(visitService.cancelVisit(visiId));
    }

    @GetMapping("/history")
    public ResponseEntity<List<Visit>> getHistoryOfVisit(@Valid @RequestParam Long patientId){
        return ResponseEntity.ok(visitService.getHistoryOfVisit(patientId));

    }
}
