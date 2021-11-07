package com.mycompany.mszczepienia.dto.address;

import com.mycompany.mszczepienia.dto.city.CityDto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
public class AddressDto {

    @NotNull
    @Positive
    private Long id;

    private CityDto city;

    private String street;

    private String number;
}
