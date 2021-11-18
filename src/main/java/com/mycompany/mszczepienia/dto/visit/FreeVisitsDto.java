package com.mycompany.mszczepienia.dto.visit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FreeVisitsDto {

    private LocalDate date;
    private List<LocalTime> visitHours;
}
