package com.mycompany.mszczepienia.repository;

import com.mycompany.mszczepienia.model.City;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    @EntityGraph("city.voivodeship")
    List<City> findAllByNameContainingIgnoreCaseOrderByName(String city);
}
