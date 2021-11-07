package com.mycompany.mszczepienia.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@Setter
@Immutable
@NamedEntityGraph(
        name = "city.voivodeship",
        attributeNodes = @NamedAttributeNode("voivodeship")
)
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable = false)
    @NotBlank
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Voivodeship voivodeship;
}
