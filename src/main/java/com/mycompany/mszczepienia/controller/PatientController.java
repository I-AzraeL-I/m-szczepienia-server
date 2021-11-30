package com.mycompany.mszczepienia.controller;

import com.mycompany.mszczepienia.dto.patient.PatientDto;
import com.mycompany.mszczepienia.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/patient")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @GetMapping("")
    public ResponseEntity<List<PatientDto>> getPatientsByUserEmail(@RequestParam String email) {
        return ResponseEntity.ok(patientService.findAllByEmail(email));
    }
}
