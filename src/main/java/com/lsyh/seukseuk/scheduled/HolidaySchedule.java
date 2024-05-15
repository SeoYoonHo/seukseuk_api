package com.lsyh.seukseuk.scheduled;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lsyh.seukseuk.dto.EmployeeDTO;
import com.lsyh.seukseuk.dto.HolidayDTO;
import com.lsyh.seukseuk.service.EmployeeService;
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
    private final EmployeeService employeeService;

    @Scheduled(cron = "0 0 0 * * ?") // 매일 자정에 실행 00:00:00
    public void setHolidayTask() throws URISyntaxException, JsonProcessingException {
        log.info("holiday setup");
        LocalDate currentDate = LocalDate.now();
        Month month = currentDate.getMonth();
        Year year = Year.of(currentDate.getYear());

        HolidayDTO.HolidayRequest holidayRequest = new HolidayDTO.HolidayRequest();
        holidayRequest.setMonth(month.getValue());
        holidayRequest.setYear(year.getValue());

//        log.info(holidayRequest.toString());

        holidayService.postHolidayList(holidayRequest);
    }

    @Scheduled(cron = "0 10 0 * * ?") // 매일 00:10에 실행
    public void setEmployeeWorkingDayTask() {
        log.info("employee Working Day setup");
        LocalDate currentDate = LocalDate.now();
        Month month = currentDate.getMonth();
        Year year = Year.of(currentDate.getYear());

        for(EmployeeDTO.EmployeeResponse employeeResponse : employeeService.getEmployeeList()){
            EmployeeDTO.EmployeeWorkingDayRequest employeeWorkingDayRequest = new EmployeeDTO.EmployeeWorkingDayRequest();
            employeeWorkingDayRequest.setYear(year.getValue());
            employeeWorkingDayRequest.setMonth(month.getValue());
            employeeWorkingDayRequest.setName(employeeResponse.getName());

            employeeService.calculateEmployeeWorkingDay(employeeWorkingDayRequest);
        }
    }
}
