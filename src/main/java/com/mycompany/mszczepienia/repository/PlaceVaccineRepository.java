package com.mycompany.mszczepienia.repository;

import com.mycompany.mszczepienia.model.PlaceVaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceVaccineRepository extends JpaRepository<PlaceVaccine, Long> {

    boolean existsByPlace_IdAndVaccine_IdAndQuantityIsGreaterThan(Long placeId, Long vaccineId, long quantity);

    @Modifying
    @Query("UPDATE PlaceVaccine pv SET pv.quantity = pv.quantity + 1 WHERE pv.id.placeId = :placeId AND pv.id.vaccineId = :vaccineId")
    void incrementQuantity(Long placeId, Long vaccineId);

    @Modifying
    @Query("UPDATE PlaceVaccine pv SET pv.quantity = pv.quantity - 1 WHERE pv.id.placeId = :placeId AND pv.id.vaccineId = :vaccineId AND pv.quantity > 0")
    int decrementQuantity(Long placeId, Long vaccineId);
}
