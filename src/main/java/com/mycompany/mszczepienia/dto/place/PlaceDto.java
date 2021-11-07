package com.mycompany.mszczepienia.dto.place;

import com.mycompany.mszczepienia.dto.address.AddressDto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
public class PlaceDto {

    @NotNull
    @Positive
    private Long id;

    private String name;

    private AddressDto address;
}
