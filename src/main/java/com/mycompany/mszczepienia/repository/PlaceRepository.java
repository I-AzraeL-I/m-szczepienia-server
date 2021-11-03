package com.mycompany.mszczepienia.repository;

import com.mycompany.mszczepienia.model.Place;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {

    @EntityGraph("place.address.city.voivodeship")
    List<Place> findAllByAddress_City_IdOrderByName(Long cityId);
}
