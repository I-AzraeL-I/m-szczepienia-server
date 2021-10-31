package com.mycompany.mszczepienia.repository;

import com.mycompany.mszczepienia.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    boolean existsByPesel(String pesel);
}
