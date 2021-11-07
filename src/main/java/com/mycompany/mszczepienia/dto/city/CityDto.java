package com.mycompany.mszczepienia.dto.city;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
public class CityDto {

    @NotNull
    @Positive
    private Long id;

    private String name;

    private VoivodeshipDto voivodeship;
}
