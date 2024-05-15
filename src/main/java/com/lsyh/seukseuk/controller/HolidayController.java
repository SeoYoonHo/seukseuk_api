package com.lsyh.seukseuk.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lsyh.seukseuk.common.CommonResponse;
import com.lsyh.seukseuk.dto.HolidayDTO;
import com.lsyh.seukseuk.service.HolidayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/holiday")
@RequiredArgsConstructor
public class HolidayController {

    private final HolidayService holidayService;

    @GetMapping("/holidayList")
    public ResponseEntity<CommonResponse.DataResponse<List<HolidayDTO.HolidayResponse>>> getHolidayList(@RequestParam Integer year, @RequestParam Integer month) {
        return ResponseEntity.ok(CommonResponse.DataResponse.of("001", "Success", holidayService.getHolidayList(year, month)));
    }

    @PostMapping("/holidayList")
    public ResponseEntity<CommonResponse.DataResponse<Map<String, Integer>>> postHolidayList(@RequestBody HolidayDTO.HolidayRequest holidayRequest) throws JsonProcessingException, URISyntaxException {
        Integer updateHolidayCount = holidayService.postHolidayList(holidayRequest);
        Map<String, Integer> countMap = new HashMap<>();
        countMap.put("holiday count", updateHolidayCount);
        return ResponseEntity.ok(CommonResponse.DataResponse.of("003", "Update holiday success", countMap));
    }
}
