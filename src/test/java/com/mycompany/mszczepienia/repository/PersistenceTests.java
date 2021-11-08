package com.mycompany.mszczepienia.repository;

import com.mycompany.mszczepienia.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("localTests")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class PersistenceTests {

    @Autowired private TestEntityManager entityManager;
    @Autowired private CityRepository cityRepository;
    @Autowired private UserRepository userRepository;

    @Test
    public void testAddCity() {
        final String cityName = "Warszawa";
        var voivodeship = new Voivodeship();
        voivodeship.setName("Mazowieckie");
        entityManager.persist(voivodeship);
        entityManager.flush();
        var city = new City();
        city.setName(cityName);
        city.setVoivodeship(voivodeship);

        var persistedCity = cityRepository.saveAndFlush(city);

        assertEquals(1, cityRepository.count());
        assertTrue(cityRepository.findById(persistedCity.getId()).isPresent());
        assertEquals(cityName, cityRepository.findById(persistedCity.getId()).get().getName());
    }

    @Test
    public void testAddUser() {
        final String email = "email@email.com";
        final String password = "password";
        final String role = "ROLE_USER";

        var user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);

        var persistedUser = userRepository.saveAndFlush(user);

        assertEquals(1, userRepository.count());
        assertTrue(userRepository.findById(persistedUser.getId()).isPresent());
        assertEquals(email, userRepository.findById(persistedUser.getId()).get().getEmail());
        assertEquals(password, userRepository.findById(persistedUser.getId()).get().getPassword());
        assertEquals(role, userRepository.findById(persistedUser.getId()).get().getRole());
    }

    @Test
    public void testAddPatient() {
        final String cityName = "Warszawa";
        var voivodeship = new Voivodeship();
        voivodeship.setName("Mazowieckie");
        entityManager.persist(voivodeship);
        entityManager.flush();
        var city = new City();
        city.setName(cityName);
        city.setVoivodeship(voivodeship);
        var persistedCity = cityRepository.saveAndFlush(city);

        final String email = "email@email.com";
        final String password = "password";
        final String role = "ROLE_USER";
        var user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);
        var persistedUser = userRepository.saveAndFlush(user);

        final String street = "Polna";
        final String number = "1A";
        var address = new Address();
        address.setCity(persistedCity);
        address.setStreet(street);
        address.setNumber(number);

        final String firstName = "Jan";
        final String lastName = "Kowalski";
        final String pesel = "81091299465";
        var patient = new Patient();
        patient.setAddress(address);
        patient.setFirstName(firstName);
        patient.setLastName(lastName);
        patient.setPesel(pesel);

        persistedUser.addPatient(patient);
        userRepository.saveAndFlush(persistedUser);

        assertTrue(userRepository.findById(persistedUser.getId()).isPresent());
        var patients = userRepository.findById(persistedUser.getId()).get().getPatients();
        assertEquals(1, patients.size());
        assertEquals(firstName, patients.get(0).getFirstName());
        assertEquals(lastName, patients.get(0).getLastName());
        assertEquals(pesel, patients.get(0).getPesel());
        assertEquals(street, patients.get(0).getAddress().getStreet());
        assertEquals(number, patients.get(0).getAddress().getNumber());
        assertEquals(cityName, patients.get(0).getAddress().getCity().getName());
    }

}
