package com.mycompany.mszczepienia.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PlaceVaccine {

    @EmbeddedId
    private PlaceVaccineId id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("vaccineId")
    private Vaccine vaccine;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("placeId")
    private Place place;

    @Column(nullable = false)
    private long quantity;

    public PlaceVaccine(Place place, Vaccine vaccine, long quantity) {
        this.vaccine = vaccine;
        this.place = place;
        this.id = new PlaceVaccineId(place.getId(), vaccine.getId());
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlaceVaccine that = (PlaceVaccine) o;
        return Objects.equals(place, that.place) && Objects.equals(vaccine, that.vaccine);
    }

    @Override
    public int hashCode() {
        return Objects.hash(place, vaccine);
    }
}
