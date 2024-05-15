package com.lsyh.seukseuk.service;

import com.lsyh.seukseuk.domain.Employee;
import com.lsyh.seukseuk.domain.Holiday;
import com.lsyh.seukseuk.dto.EmployeeDTO;
import com.lsyh.seukseuk.mapper.EmployeeMapper;
import com.lsyh.seukseuk.repository.EmployeeRepository;
import com.lsyh.seukseuk.repository.HolidayRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final HolidayRepository holidayRepository;

    public List<EmployeeDTO.EmployeeResponse> getEmployeeList() {
        List<Employee> employeeList = employeeRepository.findAll();
        log.info(employeeList.toString());
        return employeeList.stream()
                           .map(emp -> EmployeeMapper.INSTANCE.employeeToEmployeeResponseDto(emp))
                           .toList();
    }

    public List<EmployeeDTO.EmployeeSalaryResponse> getEmployeesSalary() {
        LocalDate currentDate = LocalDate.now();

        List<Employee> employeeList = employeeRepository.findAll();
        log.info(employeeList.toString());
        List<EmployeeDTO.EmployeeSalaryResponse> employeeSalaryResponseList = employeeList.stream()
                                                                                          .map(employee -> setSalary(employee, currentDate))
                                                                                          .toList();
        return employeeSalaryResponseList;
    }

    public EmployeeDTO.EmployeeSalaryResponse setSalary(Employee employee, LocalDate currentDate ) {
        Month month = currentDate.getMonth();
        Year year = Year.of(currentDate.getYear());

        EmployeeDTO.EmployeeSalaryResponse employeeSalaryResponse = EmployeeMapper.INSTANCE.employeeToEmployeeSalaryResponseDto(employee);
        Map<DayOfWeek, Integer> dailyWages = employeeSalaryResponse.getDailyWages();
        List<LocalDate> holidayList = holidayRepository.findHolidaysByMonthAndYear(month.getValue(), year.getValue())
                                                       .stream()
                                                       .map(Holiday::getDate)
                                                       .toList();

        Integer salary = 0;
        LocalDate startOfMonth = LocalDate.of(year.getValue(), month, 1);
        LocalDate endOfMonth = startOfMonth.with(TemporalAdjusters.lastDayOfMonth());

        for (LocalDate date = startOfMonth; date.isBefore(endOfMonth.plusDays(1)); date = date.plusDays(1)) {
            if (dailyWages.keySet()
                          .contains(date.getDayOfWeek()) && !holidayList.contains(date)) {
                salary += dailyWages.get(date.getDayOfWeek());
            }
        }
        employeeSalaryResponse.setSalary(salary);

        return employeeSalaryResponse;
    }
}
