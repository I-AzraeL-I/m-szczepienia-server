package com.mycompany.mszczepienia.dto.vaccine;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
public class VaccineDto {

    @NotNull
    @Positive
    private Long id;

    private DiseaseDto disease;

    private ManufacturerDto manufacturer;
}
