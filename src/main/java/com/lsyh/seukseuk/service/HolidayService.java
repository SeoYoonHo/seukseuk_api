package com.lsyh.seukseuk.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lsyh.seukseuk.api.ApiUtil;
import com.lsyh.seukseuk.api.GoHolidayResponse;
import com.lsyh.seukseuk.domain.Holiday;
import com.lsyh.seukseuk.dto.HolidayDTO;
import com.lsyh.seukseuk.mapper.HolidayMapper;
import com.lsyh.seukseuk.repository.HolidayRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class HolidayService {

    private final HolidayRepository holidayRepository;
    private final ObjectMapper objectMapper;
    private final ApiUtil apiUtil;

    public List<HolidayDTO.HolidayResponse> getHolidayList(int year, int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();

        List<Holiday> holidayList = holidayRepository.findByDateBetween(startDate, endDate);
        return holidayList.stream()
                          .map(holiday -> HolidayMapper.INSTANCE.holidayToHolidayResponseDto(holiday))
                          .toList();
    }

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
