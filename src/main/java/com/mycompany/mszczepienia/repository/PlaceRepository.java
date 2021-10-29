package com.mycompany.mszczepienia.repository;

import com.mycompany.mszczepienia.model.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {
}
