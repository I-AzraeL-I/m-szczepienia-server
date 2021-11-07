package com.mycompany.mszczepienia.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@NamedEntityGraph(
        name = "place.address.city.voivodeship",
        attributeNodes = @NamedAttributeNode(value = "address", subgraph = "address.city"),
        subgraphs = {
                @NamedSubgraph(
                        name = "address.city",
                        attributeNodes = @NamedAttributeNode(value = "city", subgraph = "city.voivodeship")),
                @NamedSubgraph(
                        name = "city.voivodeship",
                        attributeNodes = @NamedAttributeNode("voivodeship"))
        }
)
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
    private List<PlaceVaccine> vaccines = new ArrayList<>();

    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkDay> workDays = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "moderator_id")
    private User moderator;

    public void addVaccine(Vaccine vaccine, long quantity) {
        var placeVaccine = new PlaceVaccine(this, vaccine, quantity);
        vaccines.add(placeVaccine);
        vaccine.getPlaces().add(placeVaccine);
    }

    public void removeVaccine(Vaccine vaccine) {
        for (Iterator<PlaceVaccine> iterator = vaccines.iterator(); iterator.hasNext(); ) {
            PlaceVaccine placeVaccine = iterator.next();
            if (placeVaccine.getPlace().equals(this) && placeVaccine.getVaccine().equals(vaccine)) {
                iterator.remove();
                placeVaccine.getVaccine().getPlaces().remove(placeVaccine);
                placeVaccine.setPlace(null);
                placeVaccine.setVaccine(null);
            }
        }
    }

    public void addWorkDay(WorkDay workDay) {
        workDays.add(workDay);
        workDay.setPlace(this);
    }

    public void removeWorkDay(WorkDay workDay) {
        workDays.remove(workDay);
        workDay.setPlace(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Place place = (Place) o;
        return Objects.equals(name, place.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
