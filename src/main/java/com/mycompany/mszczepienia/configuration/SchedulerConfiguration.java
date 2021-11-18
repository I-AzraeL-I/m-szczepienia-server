package com.mycompany.mszczepienia.configuration;

import com.mycompany.mszczepienia.service.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PostConstruct;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class SchedulerConfiguration {

    private final VisitService visitService;

    @PostConstruct
    public void onStartup() {
        visitService.updateMissedVisitsStatus();
    }

    @Scheduled(cron = "@daily", zone = "${app.usedTimezone}")
    public void scheduleDailyAtMidnight() {
        visitService.updateMissedVisitsStatus();
    }
}
