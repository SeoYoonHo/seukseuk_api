package com.lsyh.seukseuk.controller;

import com.lsyh.seukseuk.dto.EmployeeDTO;
import com.lsyh.seukseuk.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;


    @PostMapping("/allList")
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

    @PostMapping("/allSalaryList")
    public ResponseEntity<String> getEmployeeSalary() {
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
}
