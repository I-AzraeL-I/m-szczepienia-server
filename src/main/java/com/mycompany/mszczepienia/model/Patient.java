package com.mycompany.mszczepienia.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.pl.PESEL;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "First name is mandatory")
    @Size(min = 3, max = 100, message = "First name length must be between {min} and {max}")
    private String firstName;

    @Column(nullable = false)
    @NotBlank(message = "Last name is mandatory")
    @Size(min = 3, max = 100, message = "Last name length must be between {min} and {max}")
    private String lastName;

    @Column(unique = true)
    @PESEL(message = "Invalid pesel format")
    private String pesel;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "address_id")
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User user;

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
