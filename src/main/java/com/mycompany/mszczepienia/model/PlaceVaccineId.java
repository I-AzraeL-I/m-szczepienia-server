package com.mycompany.mszczepienia.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlaceVaccineId implements Serializable {

    @Column(nullable = false)
    private Long placeId;

    @Column(nullable = false)
    private Long vaccineId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlaceVaccineId that = (PlaceVaccineId) o;
        return Objects.equals(placeId, that.placeId) && Objects.equals(vaccineId, that.vaccineId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(placeId, vaccineId);
    }
}
