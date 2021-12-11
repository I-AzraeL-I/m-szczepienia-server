package com.mycompany.mszczepienia.dto.visit;

import com.mycompany.mszczepienia.dto.place.PlaceDto;
import com.mycompany.mszczepienia.dto.vaccine.VaccineDto;
import com.mycompany.mszczepienia.model.VisitStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class VisitWithVaccineAndPlaceDto {

    private Long id;

    private PlaceDto place;

    private VaccineDto vaccine;

    private LocalDate date;

    private LocalTime time;

    private VisitStatus visitStatus;
}
