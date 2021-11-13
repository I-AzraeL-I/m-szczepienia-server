package com.mycompany.mszczepienia.dataloader;

import com.mycompany.mszczepienia.model.*;
import com.mycompany.mszczepienia.repository.*;
import com.mycompany.mszczepienia.security.Role;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class GenerateData {

    private final String userData = "userData.json";
    private final String cityData = "cityData.json";
    private final String addressPlaceData = "addressPlaceData.json";
    private final String addressUserData = "addressUserData.json";
    private final String patientData = "patientData.json";

    public void generateUsers(UserRepository userRepository, PatientRepository patientRepository){
        JSONArray userData = Generator.getUsersArray(this.userData);
        JSONArray patientData = Generator.getUsersArray(this.patientData);
        JSONArray addressData = Generator.getUsersArray(this.addressUserData);
        for(int i = 0; i< Objects.requireNonNull(userData).size() - 4; i++){
            JSONObject userObject = (JSONObject) userData.get(i);
            JSONObject patientObject = (JSONObject) patientData.get(i);
            JSONObject addressObject = (JSONObject) addressData.get(i);
            User user = new User();
            user.setEmail(userObject.get("email").toString());
            user.setPassword(userObject.get("password").toString());
            user.setRole(String.valueOf(Role.USER));
            userRepository.save(user);
            System.out.println("Elo");
            Patient patient = new Patient();
            patient.setUser(user);
            Address address = new Address();
            address.setStreet(addressObject.get("street").toString());
            address.setNumber(addressObject.get("number").toString());
            patient.setAddress(address);
            patient.setFirstName(patientObject.get("firstName").toString());
            patient.setLastName(patientObject.get("lastName").toString());
            patient.setPesel(patientObject.get("pesel").toString());
            patient.setUser(user);
        }

        for(int i=userData.size() - 4; i<userData.size(); i++){
            JSONObject jsonObject = (JSONObject) userData.get(i);
            User user = new User();
            user.setEmail(jsonObject.get("email").toString());
            user.setPassword(jsonObject.get("password").toString());
            if(i % 2 == 0){
                user.setRole(String.valueOf(Role.MODERATOR));}
            else {
                user.setRole(String.valueOf(Role.ADMIN));
            }
            userRepository.save(user);
        }
    }

    public void generateCityAndVoivodeship(CityRepository cityRepository, VoivodeshipRepository voivodeshipRepository){
        JSONArray jsonArray = Generator.getUsersArray(cityData);
        for (Object o : Objects.requireNonNull(jsonArray)) {
            JSONObject jsonObject = (JSONObject) o;
            City city = new City();
            Voivodeship voivodeship = new Voivodeship();
            voivodeship.setName(jsonObject.get("voivodeship").toString());
            voivodeshipRepository.save(voivodeship);
            city.setName(jsonObject.get("city").toString());
            city.setVoivodeship(voivodeship);
            cityRepository.save(city);
        }
    }

    public void generateVaccine(VaccineRepository vaccineRepository, ManufacturerRepository manufacturerRepository,
                                DiseaseRepository diseaseRepository){
        List<String> manufacturers = List.of("Pfizer", "Johnson & Johnson", "Moderna", "AstraZeneca");
        for(String man: manufacturers){
            Manufacturer manufacturer = new Manufacturer();
            manufacturer.setName(man);
            manufacturerRepository.save(manufacturer);
        }

        Disease disease = new Disease();
        disease.setName("COVID19");
        diseaseRepository.save(disease);
        List<Manufacturer> list = manufacturerRepository.findAll();
        for(Manufacturer manufacturer: list){
            Vaccine vaccine = new Vaccine();
            vaccine.setDisease(disease);
            vaccine.setManufacturer(manufacturer);
            vaccineRepository.save(vaccine);
        }
    }

    public void generatePlace(CityRepository cityRepository, PlaceRepository placeRepository, VaccineRepository vaccineRepository){
        List<City> cities = cityRepository.findAll();
        List<Vaccine> vaccines = vaccineRepository.findAll();
        Random random = new Random();
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
            for(int i=0; i<random.nextInt(vaccines.size()); i++ ){
                place.addVaccine(vaccines.get(i), random.nextInt(100));
            }
            placeRepository.save(place);
        }
    }

}
