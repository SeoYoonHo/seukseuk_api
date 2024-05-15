package com.lsyh.seukseuk.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lsyh.seukseuk.api.ApiUtil;
import com.lsyh.seukseuk.api.GoHolidayResponse;
import com.lsyh.seukseuk.domain.Employee;
import com.lsyh.seukseuk.domain.EmployeeWorkingDay;
import com.lsyh.seukseuk.domain.Holiday;
import com.lsyh.seukseuk.dto.HolidayDTO;
import com.lsyh.seukseuk.mapper.HolidayMapper;
import com.lsyh.seukseuk.repository.HolidayRepository;
import com.lsyh.seukseuk.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URISyntaxException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class HolidayService {

    private final HolidayRepository holidayRepository;
    private final ObjectMapper objectMapper;
    private final ApiUtil apiUtil;

    /**
     * 특정 연월의 휴일목록을 가져오는 함수
     *
     * @param year  연도
     * @param month 달
     * @return 해당 연월의 휴일목록
     */
    public List<HolidayDTO.HolidayResponse> getHolidayList(int year, int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();

        List<Holiday> holidayList = holidayRepository.findByDateBetween(startDate, endDate);
        return holidayList.stream()
                          .map(holiday -> HolidayMapper.INSTANCE.holidayToHolidayResponseDto(holiday))
                          .toList();
    }

    /**
     * 특정 연월의 휴일목록 공공 api를 통해 셋팅하는 함수
     *
     * @param holidayRequest 연,월을 가진 dto
     * @return 공공 api로부터 받은 휴일숫자
     */
    public Integer postHolidayList(HolidayDTO.HolidayRequest holidayRequest) throws JsonProcessingException, URISyntaxException {
        ResponseEntity<String> responseEntity = apiUtil.fetchAndSaveHolidays(holidayRequest.getYear(), holidayRequest.getMonth());
        String response = responseEntity.getBody();

        GoHolidayResponse holidayResponse = objectMapper.readValue(response, GoHolidayResponse.class);

        List<GoHolidayResponse.Item> items = holidayResponse.getResponse()
                                                            .getBody()
                                                            .getItems()
                                                            .getItem();
        for (GoHolidayResponse.Item item : items) {
            Holiday holiday = new Holiday();
            holiday.setDate(item.getLocdate());
            holiday.setDescription(item.getDateName());
            holidayRepository.save(holiday);
        }

        return items.size();
    }
}
