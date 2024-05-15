package com.lsyh.seukseuk.service;

import com.lsyh.seukseuk.domain.Employee;
import com.lsyh.seukseuk.domain.EmployeeWorkingDay;
import com.lsyh.seukseuk.domain.Holiday;
import com.lsyh.seukseuk.dto.EmployeeDTO;
import com.lsyh.seukseuk.mapper.EmployeeMapper;
import com.lsyh.seukseuk.repository.EmployeeRepository;
import com.lsyh.seukseuk.repository.EmployeeWorkingDayRepository;
import com.lsyh.seukseuk.repository.HolidayRepository;
import com.lsyh.seukseuk.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final HolidayRepository holidayRepository;
    private final EmployeeWorkingDayRepository employeeWorkingDayRepository;

    /**
     * 모든 선생님 목록 가져오는 함수
     *
     * @return 선생님 목록 문자열
     */
    public List<EmployeeDTO.EmployeeResponse> getEmployeeList() {
        List<Employee> employeeList = employeeRepository.findAll();
        log.info(employeeList.toString());
        return employeeList.stream()
                           .map(emp -> EmployeeMapper.INSTANCE.employeeToEmployeeResponseDto(emp))
                           .toList();
    }

    /**
     * 모든 선생님의 급여가 포함된 목록을 가져오는 함수
     *
     * @return 선생님 목록 + 급여 목록 문자열
     */
    public List<EmployeeDTO.EmployeeSalaryResponse> getEmployeesSalary() {
        LocalDate currentDate = LocalDate.now();

        List<Employee> employeeList = employeeRepository.findAll();
        log.info(employeeList.toString());
        List<EmployeeDTO.EmployeeSalaryResponse> employeeSalaryResponseList = employeeList.stream()
                                                                                          .map(employee -> setSalary(employee, currentDate))
                                                                                          .toList();
        return employeeSalaryResponseList;
    }


    /**
     * 선생님 급여를 계산해서 적용하는 함수(현재 날짜를 기준으로, 해당 월급 계산)
     *
     * @param employee    급여를 계산할 선생님
     * @param currentDate 현재 날짜
     * @return 선생님 목록 + 급여 목록 문자열
     */
    public EmployeeDTO.EmployeeSalaryResponse setSalary(Employee employee, LocalDate currentDate) {
        Month month = currentDate.getMonth();
        Year year = Year.of(currentDate.getYear());
        LocalDate startOfMonth = LocalDate.of(year.getValue(), month, 1);
        LocalDate endOfMonth = startOfMonth.with(TemporalAdjusters.lastDayOfMonth());

        List<EmployeeWorkingDay> employeeWorkingDayList = employeeWorkingDayRepository.findEmployeeWorkingDaysByEmployeeAndDateRange(employee.getId(), startOfMonth, endOfMonth);
        Map<DayOfWeek, Integer> dailyWages = employee.getDailyWages();
        Integer salary = 0;
        for (EmployeeWorkingDay employeeWorkingDay : employeeWorkingDayList) {
            if (employeeWorkingDay.isWorking()) {
                salary += dailyWages.get(employeeWorkingDay.getDate().getDayOfWeek());
            }
        }

        EmployeeDTO.EmployeeSalaryResponse employeeSalaryResponse = EmployeeMapper.INSTANCE.employeeToEmployeeSalaryResponseDto(employee);
        employeeSalaryResponse.setSalary(salary);

        return employeeSalaryResponse;
    }

    /**
     * 선생님들의 일하는 날짜를 계산하는 함수(특정 월 기준)
     * 1. 해당 연월에 일하는 날짜가 없을 경우
     * - 기본값으로 계산해서 insert(기본값: 일하는 요일, 휴일 반영)
     * 2. 해당 연월에 일하는 날짜가 이미 있을 경우
     * - 기본값으로 계산은 하되, 기존값은 건드리지 않음
     * - 기존 값 이외의 날짜가 새로 계산이 될 경우에는 insert
     *
     * @param employeeWorkingDayRequest 계산할 선생님, 연도, 월(휴일 계산할 때 같이 계산하려 함)
     */
    public Integer calculateEmployeeWorkingDay(EmployeeDTO.EmployeeWorkingDayRequest employeeWorkingDayRequest) {
        // 0. 선생님 객체부터 가져오기
        Optional<Employee> optionalEmployee = employeeRepository.findEmployeeByName(employeeWorkingDayRequest.getName());
        Employee employee = optionalEmployee.orElseThrow();

        LocalDate startOfMonth = LocalDate.of(employeeWorkingDayRequest.getYear(), employeeWorkingDayRequest.getMonth(), 1);
        LocalDate endOfMonth = startOfMonth.with(TemporalAdjusters.lastDayOfMonth());
        List<EmployeeWorkingDay> AlreadyEmployeeWorkingDayList = employeeWorkingDayRepository.findEmployeeWorkingDaysByEmployeeAndDateRange(employee.getId(), startOfMonth, endOfMonth);

        // 1. 휴일목록 가져오기
        YearMonth yearMonth = YearMonth.of(employeeWorkingDayRequest.getYear(), employeeWorkingDayRequest.getMonth());
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();

        List<Holiday> holidayList = holidayRepository.findByDateBetween(startDate, endDate);

        // 2. 일하는 날짜 가져오기
        Map<DayOfWeek, Integer> dailyWages = employee.getDailyWages();
        Map<DayOfWeek, List<LocalDate>> workingDayMap = Util.getDatesForDaysOfWeek(employeeWorkingDayRequest.getYear(), employeeWorkingDayRequest.getMonth(), dailyWages.keySet());

        // 3. 일하는 날짜 - 휴일 목록 = 진짜 일하는 날짜
        List<EmployeeWorkingDay> employeeWorkingDayList = new ArrayList<>();
        for (DayOfWeek dayOfWeek : workingDayMap.keySet()) {
            for (LocalDate workingDay : workingDayMap.get(dayOfWeek)) {

                // 이미 존재하는 경우 건너뛰기
                boolean isOk = true;
                for(EmployeeWorkingDay alreadyEmployeeWorkingDay : AlreadyEmployeeWorkingDayList){
                    if(alreadyEmployeeWorkingDay.getDate().equals(workingDay)) {
                        isOk = false;
                        AlreadyEmployeeWorkingDayList.remove(alreadyEmployeeWorkingDay);
                        break;
                    }
                }
                if(!isOk){
                    continue;
                }

                // 존재하지 않는 경우만 아래 로직 수행
                EmployeeWorkingDay employeeWorkingDay = new EmployeeWorkingDay();
                employeeWorkingDay.setEmployee(employee);
                employeeWorkingDay.setDate(workingDay);
                employeeWorkingDay.setWorking(true);

                for (Holiday holiday : holidayList) {
                    if (workingDay.equals(holiday.getDate())) {
                        employeeWorkingDay.setWorking(false);
                        holidayList.remove(holiday);
                    }
                }

                employeeWorkingDayList.add(employeeWorkingDay);
            }
        }

        // 4. 일하는 날짜 save(있으면 그대로, 없으면 insert)
        employeeWorkingDayRepository.saveAll(employeeWorkingDayList);

        return employeeWorkingDayList.size();
    }
}
