package com.mycompany.mszczepienia.dto.visit;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class CreateVisitDto {

    @NotNull(message = "date cannot be null")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @FutureOrPresent(message = "date must be future or present value")
    private LocalDate date;

    @NotNull(message = "time cannot be null")
    private LocalTime time;

    @NotNull(message = "placeId cannot be null")
    @Positive
    private Long placeId;

    @NotNull(message = "vaccineId cannot be null")
    @Positive
    private Long vaccineId;

    @NotNull(message = "patientId cannot be null")
    @Positive
    private Long patientId;
}
