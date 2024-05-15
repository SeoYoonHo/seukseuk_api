package com.lsyh.seukseuk.controller;

import com.lsyh.seukseuk.dto.EmployeeDTO;
import com.lsyh.seukseuk.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/bot")
@RequiredArgsConstructor
public class BotController {

    private final EmployeeService employeeService;

    @GetMapping("/emp/allList")
    public ResponseEntity<String> getAllEmployeeList() {
        List<EmployeeDTO.EmployeeResponse> employeeList = employeeService.getEmployeeList();

        StringBuilder sb = new StringBuilder();
        for (EmployeeDTO.EmployeeResponse employeeResponse : employeeList) {
            sb.append(employeeResponse.toString());
            sb.append("\n\n");
        }

        sb.deleteCharAt(sb.length() - 1);
        sb.deleteCharAt(sb.length() - 1);

        return ResponseEntity.ok(sb.toString());
    }

    @GetMapping("/emp/salaryList")
    public ResponseEntity<String> getEmployeeSalaryList() {
        List<EmployeeDTO.EmployeeSalaryResponse> employeeSalaryResponseList = employeeService.getEmployeesSalary();

        StringBuilder sb = new StringBuilder();
        for (EmployeeDTO.EmployeeSalaryResponse employeeSalaryResponse : employeeSalaryResponseList) {
            sb.append(employeeSalaryResponse.toString());
            sb.append("\n\n");
        }

        sb.deleteCharAt(sb.length() - 1);
        sb.deleteCharAt(sb.length() - 1);

        return ResponseEntity.ok(sb.toString());
    }

    @GetMapping("/helpMsg")
    public ResponseEntity<String> getHelpMessage(){
        StringBuilder sb = new StringBuilder();

        sb.append("--- 조회 명령어 --- \n");
        sb.append("/선생님목록 : 모든 선생님 목록\n");
        sb.append("/선생님급여 : 이번달 모든 선생님 급여\n");
        sb.append("/근무일 name : 이번달 name 근무일(준비중) \nex) /근무일 이하윤\n\n");

        sb.append("--- 수정 명령어 --- \n");
        sb.append("/선생님휴일추가 name date : 이번달 name 선생님 휴일추가(준비중) \nex) /선생님휴일추가 이하윤 0530\n\n");

        sb.append("--- 도움 명령어 --- \n");
        sb.append("/h : 명령어 모음");

        return ResponseEntity.ok(sb.toString());
    }
}
