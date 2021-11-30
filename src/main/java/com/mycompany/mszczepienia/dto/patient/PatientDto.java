package com.mycompany.mszczepienia.dto.patient;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String pesel;

    private boolean isMainProfile;
}
