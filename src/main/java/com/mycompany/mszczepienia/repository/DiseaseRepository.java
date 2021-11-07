package com.mycompany.mszczepienia.repository;

import com.mycompany.mszczepienia.model.Disease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiseaseRepository extends JpaRepository<Disease, Long> {

    List<Disease> findAllByNameContainingIgnoreCaseOrderByName(String name);
}
