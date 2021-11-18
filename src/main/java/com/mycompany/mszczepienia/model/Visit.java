package com.mycompany.mszczepienia.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NamedEntityGraphs({
        @NamedEntityGraph(
                name = "visit.vaccine.(disease+manufacturer)+place",
                attributeNodes = {
                        @NamedAttributeNode("place"),
                        @NamedAttributeNode(value = "vaccine", subgraph = "vaccineDetails")
                },
                subgraphs = {
                        @NamedSubgraph(
                                name = "vaccineDetails",
                                attributeNodes = {
                                        @NamedAttributeNode("manufacturer"),
                                        @NamedAttributeNode("disease")
                                })
                }
        ),
        @NamedEntityGraph(
                name = "visit.vaccine+place",
                attributeNodes = {
                        @NamedAttributeNode("place"),
                        @NamedAttributeNode("vaccine")
                }
        )
})
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Place place;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Vaccine vaccine;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Patient patient;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime time;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private VisitStatus visitStatus;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Visit)) return false;
        return id != null && id.equals(((Visit) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
