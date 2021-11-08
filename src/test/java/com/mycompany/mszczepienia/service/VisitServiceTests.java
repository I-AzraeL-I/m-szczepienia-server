package com.mycompany.mszczepienia.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("localTests")
@Import(VisitService.class)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:data.sql")
public class VisitServiceTests {

    @Autowired
    private VisitService visitService;

    private static final Long VACCINE_IN_STOCK_ID = 1L;
    private static final Long VACCINE_OUT_OF_STOCK_ID = 4L;
    private static final Long PLACE_ID = 1L;
    private static final DayOfWeek EXCLUDED_WORKDAY = DayOfWeek.SUNDAY;
    private static final DayOfWeek VALID_WORKDAY = DayOfWeek.MONDAY;
    private static final LocalTime VALID_WORKDAY_START = LocalTime.of(8, 0);
    private static final LocalTime VALID_WORKDAY_END = LocalTime.of(16, 0);
    private static final int VISIT_LENGTH_MIN = 5;

    @Test
    public void testShouldReturnEmptyList_DateIsInPast() {
        final var pastDate = LocalDate.now().minusDays(1);
        final var freeVisits = visitService.findFreeVisits(pastDate, PLACE_ID, VACCINE_IN_STOCK_ID);
        assertEquals(pastDate, freeVisits.getDate());
        assertTrue(freeVisits.getVisitHours().isEmpty());
    }

    @Test
    public void testShouldReturnEmptyList_DateNotInPlaceWorkdays() {
        final var dateNotInWorkday = LocalDate.now().with(TemporalAdjusters.next(EXCLUDED_WORKDAY));
        final var freeVisits = visitService.findFreeVisits(dateNotInWorkday, PLACE_ID, VACCINE_IN_STOCK_ID);
        assertEquals(dateNotInWorkday, freeVisits.getDate());
        assertTrue(freeVisits.getVisitHours().isEmpty());
    }

    @Test
    public void testShouldReturnEmptyList_VaccineOutOfStock() {
        final var validDate = LocalDate.now().with(TemporalAdjusters.next(VALID_WORKDAY));
        final var freeVisits = visitService.findFreeVisits(validDate, PLACE_ID, VACCINE_OUT_OF_STOCK_ID);
        assertEquals(validDate, freeVisits.getDate());
        assertTrue(freeVisits.getVisitHours().isEmpty());
    }

    @Test
    public void testShouldReturnFullList() {
        final var validDate = LocalDate.now().with(TemporalAdjusters.next(VALID_WORKDAY));
        final var freeVisits = visitService.findFreeVisits(validDate, PLACE_ID, VACCINE_IN_STOCK_ID);
        final var numberOfPossibleVisits = ChronoUnit.MINUTES.between(VALID_WORKDAY_START, VALID_WORKDAY_END) / VISIT_LENGTH_MIN;
        assertEquals(validDate, freeVisits.getDate());
        assertEquals(numberOfPossibleVisits, freeVisits.getVisitHours().size());
    }
}
