package com.mycompany.mszczepienia.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Vaccine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Manufacturer manufacturer;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Disease disease;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Place place;

    private int quantity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vaccine )) return false;
        return id != null && id.equals(((Vaccine) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
