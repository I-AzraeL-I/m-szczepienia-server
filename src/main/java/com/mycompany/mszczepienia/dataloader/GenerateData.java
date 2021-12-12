package com.mycompany.mszczepienia.dataloader;

import com.mycompany.mszczepienia.model.*;
import com.mycompany.mszczepienia.repository.*;
import com.mycompany.mszczepienia.security.Role;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class GenerateData {

    private final String userData = "userData.json";
    private final String cityData = "cityData.json";
    private final String addressPlaceData = "addressPlaceData.json";
    private final String addressUserData = "addressUserData.json";
    private final String patientData = "patientData.json";

    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final VoivodeshipRepository voivodeshipRepository;
    private final CityRepository cityRepository;
    private final VaccineRepository vaccineRepository;
    private final ManufacturerRepository manufacturerRepository;
    private final PlaceRepository placeRepository;
    private final DiseaseRepository diseaseRepository;
    private final Random random = new Random();
    private final PasswordEncoder passwordEncoder;

    public void generateUsers() {
        JSONArray userData = Generator.getUsersArray(this.userData);
        JSONArray patientData = Generator.getUsersArray(this.patientData);
        JSONArray addressData = Generator.getUsersArray(this.addressUserData);
        List<City> cities = cityRepository.findAll();
        for (int i = 0; i < Objects.requireNonNull(userData).size() - 4; i++) {
            JSONObject userObject = (JSONObject) userData.get(i);
            JSONObject patientObject = (JSONObject) patientData.get(i);
            JSONObject addressObject = (JSONObject) addressData.get(i);
            User user = new User();
            user.setEmail(userObject.get("email").toString());
            user.setPassword(passwordEncoder.encode(userObject.get("password").toString()));
            user.setRole(Role.USER.value);
            userRepository.save(user);
            Patient patient = new Patient();
            patient.setMainProfile(true);
            patient.setFirstName(patientObject.get("firstName").toString());
            patient.setLastName(patientObject.get("lastName").toString());
            patient.setPesel(patientObject.get("pesel").toString());
            user.addPatient(patient);
        }

        for (int i = userData.size() - 4; i < userData.size(); i++) {
            JSONObject jsonObject = (JSONObject) userData.get(i);
            User user = new User();
            user.setEmail(jsonObject.get("email").toString());
            user.setPassword(jsonObject.get("password").toString());
            if (i % 2 == 0) {
                user.setRole(Role.MODERATOR.value);
            } else {
                user.setRole(Role.ADMIN.value);
            }
            userRepository.save(user);
        }
    }

    public void generateCityAndVoivodeship() {
        JSONArray jsonArray = Generator.getUsersArray(cityData);
        for (Object o : Objects.requireNonNull(jsonArray)) {
            JSONObject jsonObject = (JSONObject) o;
            City city = new City();
            Optional<Voivodeship> voivodeship = voivodeshipRepository.findByName(jsonObject.get("voivodeship").toString());
            if(voivodeship.isPresent()){
                city.setVoivodeship(voivodeship.get());
            }
            else {
                Voivodeship newVoivodeship = new Voivodeship();
                newVoivodeship.setName(jsonObject.get("voivodeship").toString());
                voivodeshipRepository.save(newVoivodeship);
                city.setVoivodeship(newVoivodeship);
            }
            city.setName(jsonObject.get("city").toString());
            cityRepository.save(city);
        }
    }

    public void generateVaccine() {
        List<String> manufacturers = List.of("Pfizer", "Johnson & Johnson", "Moderna", "AstraZeneca");
        for (String man : manufacturers) {
            Manufacturer manufacturer = new Manufacturer();
            manufacturer.setName(man);
            manufacturerRepository.save(manufacturer);
        }

        Disease disease = new Disease();
        disease.setName("COVID19");
        diseaseRepository.save(disease);
        List<Manufacturer> list = manufacturerRepository.findAll();
        for (Manufacturer manufacturer : list) {
            Vaccine vaccine = new Vaccine();
            vaccine.setDisease(disease);
            vaccine.setManufacturer(manufacturer);
            vaccineRepository.save(vaccine);
        }
    }

    public void generatePlace() {
        List<City> cities = cityRepository.findAll();
        List<Vaccine> vaccines = vaccineRepository.findAll();
        List<User> moderators = userRepository.findAllByRole(Role.MODERATOR.value);
        JSONArray jsonArray = Generator.getUsersArray(addressPlaceData);
        for (Object o : Objects.requireNonNull(jsonArray)) {
            JSONObject jsonObject = (JSONObject) o;
            Address address = new Address();
            address.setCity(cities.get(random.nextInt(cities.size())));
            address.setNumber(jsonObject.get("number").toString());
            address.setStreet(jsonObject.get("street").toString());

            Place place = new Place();
            place.setAddress(address);
            place.setName(jsonObject.get("name").toString());
            place.setModerator(moderators.get(random.nextInt(moderators.size())));
            for (int i = 0; i < random.nextInt(vaccines.size()); i++) {
                place.addVaccine(vaccines.get(i), random.nextInt(100));
            }
            placeRepository.save(place);
        }
    }

    public void generateVisit() {
        List<Place> places = placeRepository.findAll();
        List<Vaccine> vaccines = vaccineRepository.findAll();
        List<Patient> patients = patientRepository.findAll();
        Visit pendingVisit = new Visit();
        pendingVisit.setPlace(places.get(random.nextInt(places.size())));
        pendingVisit.setVisitStatus(VisitStatus.PENDING);
        pendingVisit.setTime(LocalTime.NOON);
        pendingVisit.setDate(LocalDate.now().plusDays(3));
        pendingVisit.setVaccine(vaccines.get(random.nextInt(vaccines.size())));
        patients.get(random.nextInt(patients.size())).addVisit(pendingVisit);

        Visit canceledVisit = new Visit();
        canceledVisit.setPlace(places.get(random.nextInt(places.size())));
        canceledVisit.setVisitStatus(VisitStatus.CANCELLED);
        canceledVisit.setTime(LocalTime.NOON.plusHours(10));
        canceledVisit.setDate(LocalDate.now().plusDays(12));
        canceledVisit.setVaccine(vaccines.get(random.nextInt(vaccines.size())));
        patients.get(random.nextInt(patients.size())).addVisit(canceledVisit);

        Visit missedVisit = new Visit();
        missedVisit.setPlace(places.get(random.nextInt(places.size())));
        missedVisit.setVisitStatus(VisitStatus.MISSED);
        missedVisit.setTime(LocalTime.NOON.plusHours(12));
        missedVisit.setDate(LocalDate.now().plusDays(10));
        missedVisit.setVaccine(vaccines.get(random.nextInt(vaccines.size())));
        patients.get(random.nextInt(patients.size())).addVisit(missedVisit);
    }

    public void generateWorkDays() {
        List<Place> places = placeRepository.findAll();
        for (Place place : places) {
            for (int j = 0; j < 7; j++) {
                WorkDay workDay = new WorkDay();
                workDay.setDayOfWeek(DayOfWeek.MONDAY.plus(j));
                workDay.setOpeningHour(LocalTime.of(8, 0));
                workDay.setClosingHour(LocalTime.of(17, 0));
                workDay.setPlace(place);
                place.addWorkDay(workDay);
            }
        }
    }

}
