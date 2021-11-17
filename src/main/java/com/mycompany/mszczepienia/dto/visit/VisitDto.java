package com.mycompany.mszczepienia.dto.visit;

import com.mycompany.mszczepienia.model.VisitStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class VisitDto {

    private LocalDate date;

    private LocalTime time;

    private VisitStatus visitStatus;
}
