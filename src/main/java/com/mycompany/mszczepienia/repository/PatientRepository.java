package com.mycompany.mszczepienia.repository;

import com.mycompany.mszczepienia.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    boolean existsByPesel(String pesel);

    Optional<Patient> findByIdAndUser_Email(Long patientId, String email);

    List<Patient> findAllByUser_Email(String email);
}
