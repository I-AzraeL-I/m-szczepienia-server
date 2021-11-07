package com.mycompany.mszczepienia.repository;

import com.mycompany.mszczepienia.model.PlaceVaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlaceVaccineRepository extends JpaRepository<PlaceVaccine, Long> {

    Optional<PlaceVaccine> findByPlace_IdAndVaccine_Id(Long placeId, Long vaccineId);

    boolean existsByPlace_IdAndVaccine_IdAndQuantityIsGreaterThan(Long placeId, Long vaccineId, long quantity);
}
