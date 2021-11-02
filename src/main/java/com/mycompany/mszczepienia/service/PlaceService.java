package com.mycompany.mszczepienia.service;

import com.mycompany.mszczepienia.dto.place.PlaceDto;
import com.mycompany.mszczepienia.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;
    private final ModelMapper modelMapper;

    public List<PlaceDto> findAllByCityId(Long cityId) {
        var placeDtoListType = new TypeToken<List<PlaceDto>>() {}.getType();
        return modelMapper.map(placeRepository.findAllByAddress_City_IdOrderByName(cityId), placeDtoListType);
    }
}
