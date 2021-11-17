package com.mycompany.mszczepienia.dataloader;

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

    private final GenerateData generateData;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        generateData.generateCityAndVoivodeship();
        generateData.generateVaccine();
        generateData.generateUsers();
        generateData.generatePlace();
        generateData.generateWorkDays();
        generateData.generateVisit();
    }
}
