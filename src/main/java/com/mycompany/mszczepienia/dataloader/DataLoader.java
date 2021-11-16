package com.mycompany.mszczepienia.dataloader;

import com.mycompany.mszczepienia.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(value = "spring.jpa.hibernate.ddl-auto", havingValue = "create")
public class DataLoader implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final VoivodeshipRepository voivodeshipRepository;
    private final CityRepository cityRepository;
    private final VaccineRepository vaccineRepository;
    private final ManufacturerRepository manufacturerRepository;
    private final PlaceRepository placeRepository;
    private final DiseaseRepository diseaseRepository;
    private final WorkDayRepository workDayRepository;
    private final VisitRepository visitRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        GenerateData generateData = new GenerateData();
        generateData.generateUsers(userRepository, patientRepository);
        generateData.generateCityAndVoivodeship(cityRepository, voivodeshipRepository);
        generateData.generateVaccine(vaccineRepository, manufacturerRepository, diseaseRepository);
        generateData.generatePlace(cityRepository, placeRepository, vaccineRepository);
        generateData.generateVisit(visitRepository, placeRepository, vaccineRepository);
        generateData.generateWorkDays(workDayRepository, placeRepository);
    }
}
