package com.mycompany.mszczepienia.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.pl.PESEL;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable = false)
    @Size(min = 1, max = 100, message = "First name length must be between {min} and {max}")
    private String firstName;

    @Column(nullable = false)
    @Size(min = 1, max = 100, message = "Last name length must be between {min} and {max}")
    private String lastName;

    @Column(unique = true)
    @PESEL(message = "Invalid pesel format")
    private String pesel;

    @Column(nullable = false)
    private boolean isMainProfile;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Visit> visits = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User user;

    public void addVisit(Visit visit) {
        visits.add(visit);
        visit.setPatient(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Patient)) return false;
        return id != null && id.equals(((Patient) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
