package com.mycompany.mszczepienia.repository;

import com.mycompany.mszczepienia.model.WorkDay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.util.Optional;

public interface WorkDayRepository extends JpaRepository<WorkDay, Long> {

    Optional<WorkDay> findByPlace_IdAndDayOfWeek(Long placeId, DayOfWeek dayOfWeek);
}
