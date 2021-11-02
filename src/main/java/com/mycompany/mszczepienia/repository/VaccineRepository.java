package com.mycompany.mszczepienia.repository;

import com.mycompany.mszczepienia.model.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VaccineRepository extends JpaRepository<Vaccine, Long> {

    List<Vaccine> findAllByDisease_Id(Long diseaseId);
}
