package com.mycompany.mszczepienia.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable = false)
    @NotBlank
    private String name;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vaccine> vaccines = new ArrayList<>();

    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<WorkDay> workDays = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "moderator_id")
    private User moderator;

    public void addVaccine(Vaccine vaccine) {
        vaccines.add(vaccine);
        vaccine.setPlace(this);
    }

    public void removeVaccine(Vaccine vaccine) {
        vaccines.remove(vaccine);
        vaccine.setPlace(null);
    }
}