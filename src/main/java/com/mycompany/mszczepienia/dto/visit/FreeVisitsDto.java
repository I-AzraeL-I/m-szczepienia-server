package com.mycompany.mszczepienia.dto.visit;

import lombok.*;

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
