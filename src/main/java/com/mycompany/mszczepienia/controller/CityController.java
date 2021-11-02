package com.mycompany.mszczepienia.controller;

import com.mycompany.mszczepienia.dto.city.CityDto;
import com.mycompany.mszczepienia.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/city")
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;

    @GetMapping("")
    public ResponseEntity<List<CityDto>> getCitiesByName(@RequestParam String name) {
        return ResponseEntity.ok(cityService.findAllByNameContaining(name));
    }
}
