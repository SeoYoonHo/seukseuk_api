package com.lsyh.seukseuk;

import com.lsyh.seukseuk.scheduled.HolidaySchedule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class StartupRunner implements CommandLineRunner {
    private final HolidaySchedule holidaySchedule;

    @Override
    public void run(String... args) throws Exception {
        holidaySchedule.setHolidayTask();
        holidaySchedule.setEmployeeWorkingDayTask();
    }
}