package com.mycompany.mszczepienia.repository;

import com.mycompany.mszczepienia.model.Visit;
import com.mycompany.mszczepienia.model.VisitStatus;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {

    List<Visit> findAllByPlace_IdAndDateAndTimeAfterAndVisitStatusEquals(Long placeId, LocalDate date, LocalTime time, VisitStatus visitStatus);
    List<Visit> findAllByPatient_Id(Long patientId);
    boolean existsByDateAndTimeAndVisitStatus(LocalDate date, LocalTime time, VisitStatus visitStatus);

    @EntityGraph("visit.vaccine+place")
    List<Visit> findAllByDateBeforeOrDateEqualsAndTimeBeforeAndVisitStatusEquals(LocalDate date, LocalDate date2, LocalTime time, VisitStatus visitStatus);

}
