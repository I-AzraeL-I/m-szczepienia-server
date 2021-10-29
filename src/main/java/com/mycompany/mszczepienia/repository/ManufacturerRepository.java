package com.mycompany.mszczepienia.repository;

import com.mycompany.mszczepienia.model.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {
}
