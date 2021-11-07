package com.mycompany.mszczepienia.controller;

import com.mycompany.mszczepienia.dto.vaccine.DiseaseDto;
import com.mycompany.mszczepienia.service.DiseaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/disease")
@RequiredArgsConstructor
public class DiseaseController {

    private final DiseaseService diseaseService;

    @GetMapping("")
    public ResponseEntity<List<DiseaseDto>> getDiseasesByName(@RequestParam String name) {
        return ResponseEntity.ok(diseaseService.findAllByNameContaining(name));
    }
}
