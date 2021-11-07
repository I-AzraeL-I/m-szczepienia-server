package com.mycompany.mszczepienia.controller;

import com.mycompany.mszczepienia.dto.vaccine.VaccineDto;
import com.mycompany.mszczepienia.service.VaccineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vaccine")
@RequiredArgsConstructor
public class VaccineController {

    private final VaccineService vaccineService;

    @GetMapping("")
    public ResponseEntity<List<VaccineDto>> getVaccinesByDiseaseId(@RequestParam Long diseaseId) {
        return ResponseEntity.ok(vaccineService.findAllByDiseaseId(diseaseId));
    }
}
