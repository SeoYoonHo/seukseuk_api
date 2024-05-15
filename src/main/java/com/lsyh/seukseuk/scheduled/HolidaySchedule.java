package com.lsyh.seukseuk.scheduled;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lsyh.seukseuk.dto.HolidayDTO;
import com.lsyh.seukseuk.service.HolidayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;

@Component
@Slf4j
@RequiredArgsConstructor
public class HolidaySchedule {

    private final HolidayService holidayService;

    @Scheduled(cron = "0 0 0 * * ?") // 매일 자정에 실행 00:00:00
    public void setHolidayTask() throws URISyntaxException, JsonProcessingException {
        LocalDate currentDate = LocalDate.now();
        Month month = currentDate.getMonth();
        Year year = Year.of(currentDate.getYear());

        HolidayDTO.HolidayRequest holidayRequest = new HolidayDTO.HolidayRequest();
        holidayRequest.setMonth(month.getValue());
        holidayRequest.setYear(year.getValue());

//        log.info(holidayRequest.toString());

        holidayService.postHolidayList(holidayRequest);
    }
}
