package com.mycompany.mszczepienia.controller;

import com.mycompany.mszczepienia.dto.place.PlaceDto;
import com.mycompany.mszczepienia.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/place")
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceService placeService;

    @GetMapping("")
    public ResponseEntity<List<PlaceDto>> getPlacesByCityId(@RequestParam Long cityId) {
        return ResponseEntity.ok(placeService.findAllByCityId(cityId));
    }
}
