package com.mycompany.mszczepienia.service;

import com.mycompany.mszczepienia.dto.city.CityDto;
import com.mycompany.mszczepienia.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;
    private final ModelMapper modelMapper;

    public List<CityDto> findAllByNameContaining(String name) {
        var cityDtoListType = new TypeToken<List<CityDto>>() {}.getType();
        return modelMapper.map(cityRepository.findAllByNameContainingIgnoreCaseOrderByName(name), cityDtoListType);
    }
}
