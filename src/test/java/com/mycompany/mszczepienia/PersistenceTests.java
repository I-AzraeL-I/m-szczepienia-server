package com.mycompany.mszczepienia;

import com.mycompany.mszczepienia.model.City;
import com.mycompany.mszczepienia.model.Voivodeship;
import com.mycompany.mszczepienia.repository.*;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@DataJpaTest
@RequiredArgsConstructor
public class PersistenceTests {

    @PersistenceContext
    private final EntityManager entityManager;

    private final CityRepository cityRepository;
    private final DiseaseRepository diseaseRepository;
    private final ManufacturerRepository manufacturerRepository;
    private final PatientRepository patientRepository;
    private final PlaceRepository placeRepository;
    private final UserRepository userRepository;
    private final VisitRepository visitRepository;

    @Test
    public void testAddCity() {
        var voivodeship = new Voivodeship();
        voivodeship.setName("Mazowieckie");
        entityManager.persist(voivodeship);
        entityManager.flush();

        var city = new City();
        city.setName("Warszawa");
        city.setVoivodeship(voivodeship);

        cityRepository.saveAndFlush(city);

        assertEquals(1, cityRepository.count());
        assertTrue(cityRepository.findById(1L).isPresent());
        assertEquals("Warszawa", cityRepository.findById(1L).get().getName());
    }

}
