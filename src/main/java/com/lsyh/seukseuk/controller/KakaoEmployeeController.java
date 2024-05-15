package com.lsyh.seukseuk.controller;

import com.lsyh.seukseuk.common.KakaoResponse;
import com.lsyh.seukseuk.dto.KakaoDTO;
import com.lsyh.seukseuk.service.EmployeeService;
import com.lsyh.seukseuk.service.KakoEmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/employee/kakao")
@RequiredArgsConstructor
public class KakaoEmployeeController {

    private final KakoEmployeeService kakoEmployeeService;


    @PostMapping("/allList")
    public ResponseEntity<KakaoResponse> getAllEmployeeList(@RequestBody KakaoDTO.KakaoRequest kakaoRequest) {
        log.info(kakaoRequest.toString());
        KakaoResponse kakaoResponse = kakoEmployeeService.getEmployeeList();

        return ResponseEntity.ok(kakaoResponse);
    }

    @PostMapping("/salary")
    public ResponseEntity<KakaoResponse> getEmployeeSalary(@RequestBody KakaoDTO.KakaoRequest kakaoRequest) {
        log.info(kakaoRequest.toString());
        KakaoResponse kakaoResponse = kakoEmployeeService.getEmployeesSalary();

        return ResponseEntity.ok(kakaoResponse);
    }
}
