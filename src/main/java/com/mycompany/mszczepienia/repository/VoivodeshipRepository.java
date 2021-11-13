package com.mycompany.mszczepienia.repository;

import com.mycompany.mszczepienia.model.Voivodeship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoivodeshipRepository extends JpaRepository<Voivodeship, Long> {
    Optional<Voivodeship> findByName(String name);
}
