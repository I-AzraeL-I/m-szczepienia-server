package com.mycompany.mszczepienia.dto.visit;

import com.mycompany.mszczepienia.dto.patient.PatientDto;
import com.mycompany.mszczepienia.dto.vaccine.VaccineDto;
import com.mycompany.mszczepienia.model.VisitStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class VisitWithVaccineAndPatientDto {

    private Long id;

    private VaccineDto vaccine;

    private PatientDto patient;

    private LocalDate date;

    private LocalTime time;

    private VisitStatus visitStatus;
}
