package com.mycompany.mszczepienia.bean;

import org.hibernate.collection.spi.PersistentCollection;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STANDARD)
                .setSkipNullEnabled(true)
                .setPropertyCondition(context -> !(context.getSource() instanceof PersistentCollection));
        return modelMapper;
    }

}
