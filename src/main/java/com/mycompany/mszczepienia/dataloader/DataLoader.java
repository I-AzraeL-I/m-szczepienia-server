package com.mycompany.mszczepienia.dataloader;

import com.mycompany.mszczepienia.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DataLoader implements ApplicationRunner {
    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final VoivodeshipRepository voivodeshipRepository;
    private final CityRepository cityRepository;
    private final VaccineRepository vaccineRepository;
    private final ManufacturerRepository manufacturerRepository;
    private final PlaceRepository placeRepository;
    private final DiseaseRepository diseaseRepository;
    @Autowired
    public DataLoader(UserRepository userRepository, PatientRepository patientRepository, VoivodeshipRepository voivodeshipRepository, CityRepository cityRepository, VaccineRepository vaccineRepository, ManufacturerRepository manufacturerRepository, PlaceRepository placeRepository, DiseaseRepository diseaseRepository) {
        this.userRepository = userRepository;
        this.patientRepository = patientRepository;
        this.voivodeshipRepository = voivodeshipRepository;
        this.cityRepository = cityRepository;
        this.vaccineRepository = vaccineRepository;
        this.manufacturerRepository = manufacturerRepository;
        this.placeRepository = placeRepository;
        this.diseaseRepository = diseaseRepository;
    }


    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        GenerateData generateData = new GenerateData();
        generateData.generateUsers(userRepository, patientRepository);
        generateData.generateCityAndVoivodeship(cityRepository, voivodeshipRepository);
        generateData.generateVaccine(vaccineRepository, manufacturerRepository, diseaseRepository);
        generateData.generatePlace(cityRepository, placeRepository, vaccineRepository);
    }
}
