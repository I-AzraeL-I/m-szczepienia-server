package com.mycompany.mszczepienia.repository;

import com.mycompany.mszczepienia.model.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {
}
