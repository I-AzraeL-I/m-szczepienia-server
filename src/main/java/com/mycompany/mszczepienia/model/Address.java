package com.mycompany.mszczepienia.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private City city;

    @NotBlank(message = "Street is mandatory")
    @Size(min = 3, max = 50, message = "Street length must be between {min} and {max}")
    private String street;

    @NotBlank(message = "Number is mandatory")
    @Size(min = 1, max = 10, message = "Number length must be between {min} and {max}")
    private String number;
}
