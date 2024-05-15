package com.lsyh.seukseuk.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lsyh.seukseuk.common.CommonResponse;
import com.lsyh.seukseuk.dto.EmployeeDTO;
import com.lsyh.seukseuk.dto.HolidayDTO;
import com.lsyh.seukseuk.service.EmployeeService;
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
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("/calculateEmployeeWorkingDay")
    public ResponseEntity<CommonResponse.DataResponse<Map<String, Integer>>> postHolidayList(
            @RequestBody EmployeeDTO.EmployeeWorkingDayRequest employeeWorkingDayRequest) {
        Integer result = employeeService.calculateEmployeeWorkingDay(employeeWorkingDayRequest);
        Map<String, Integer> countMap = new HashMap<>();
        countMap.put("result", result);
        return ResponseEntity.ok(CommonResponse.DataResponse.of("003", "Update workingDay success", countMap));
    }
}
