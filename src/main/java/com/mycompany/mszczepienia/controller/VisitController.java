package com.mycompany.mszczepienia.controller;

import com.mycompany.mszczepienia.dto.appointment.FreeVisitsDto;
import com.mycompany.mszczepienia.service.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

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
}
