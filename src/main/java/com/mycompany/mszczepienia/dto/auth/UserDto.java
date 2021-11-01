package com.mycompany.mszczepienia.dto.auth;

import com.mycompany.mszczepienia.model.Patient;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDto {

    private Long id;
    private String email;
    private String role;
    private List<Patient> patients;
}
