package com.lsyh.seukseuk.service;

import com.lsyh.seukseuk.common.KakaoResponse;
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
public class KakoEmployeeService {
    private final EmployeeRepository employeeRepository;
    private final HolidayRepository holidayRepository;

    public KakaoResponse getEmployeeList() {
        List<Employee> employeeList = employeeRepository.findAll();
        log.info(employeeList.toString());

        KakaoResponse response = new KakaoResponse();
        KakaoResponse.Template template = new KakaoResponse.Template();

        for (Employee employee : employeeList) {
            EmployeeDTO.EmployeeResponse employeeResponse = EmployeeMapper.INSTANCE.employeeToEmployeeResponseDto(employee);
            // SimpleText 예제 설정
            KakaoResponse.Template.Output.SimpleText simpleText = new KakaoResponse.Template.Output.SimpleText();
            simpleText.setText(employeeResponse.toString());

            // Output 설정
            KakaoResponse.Template.Output output = new KakaoResponse.Template.Output();
            output.setSimpleText(simpleText);
            template.getOutputs()
                    .add(output);
        }

        // QuickReply 설정
        KakaoResponse.Template.QuickReply quickReply = new KakaoResponse.Template.QuickReply();
        quickReply.setLabel("급여정보");
        quickReply.setAction("message");
        quickReply.setMessageText("선생님 급여정보");
        template.getQuickReplies().add(quickReply);

        response.setTemplate(template);
        return response;
    }

    public KakaoResponse getEmployeesSalary() {
        LocalDate currentDate = LocalDate.now();
        Month month = currentDate.getMonth();
        Year year = Year.of(currentDate.getYear());

        List<Employee> employeeList = employeeRepository.findAll();

        KakaoResponse response = new KakaoResponse();
        KakaoResponse.Template template = new KakaoResponse.Template();

        for (Employee employee : employeeList) {
            EmployeeDTO.EmployeeSalaryResponse employeeSalaryResponse = EmployeeMapper.INSTANCE.employeeToEmployeeSalaryResponseDto(employee);
            log.info(employee.toString());
            log.info(employeeSalaryResponse.toString());
            Map<DayOfWeek, Integer> dailyWages = employeeSalaryResponse.getDailyWages();
            List<LocalDate> holidayList = holidayRepository.findHolidaysByMonthAndYear(month.getValue(), year.getValue())
                                                           .stream()
                                                           .map(Holiday::getDate)
                                                           .toList();

            Integer salary = 0;
            LocalDate startOfMonth = LocalDate.of(year.getValue(), month, 1);
            LocalDate endOfMonth = startOfMonth.with(TemporalAdjusters.lastDayOfMonth());

            for (LocalDate date = startOfMonth; date.isBefore(endOfMonth.plusDays(1)); date = date.plusDays(1)) {
                if (dailyWages.keySet().contains(date.getDayOfWeek()) && !holidayList.contains(date)) {
                    salary += dailyWages.get(date.getDayOfWeek());
                }
            }
            employeeSalaryResponse.setSalary(salary);

            // SimpleText 예제 설정
            KakaoResponse.Template.Output.SimpleText simpleText = new KakaoResponse.Template.Output.SimpleText();
            simpleText.setText(employeeSalaryResponse.toString());

            // Output 설정
            KakaoResponse.Template.Output output = new KakaoResponse.Template.Output();
            output.setSimpleText(simpleText);
            template.getOutputs()
                    .add(output);
        }

        // QuickReply 설정
//        KakaoResponse.Template.QuickReply quickReply = new KakaoResponse.Template.QuickReply();
//        quickReply.setLabel("네");
//        quickReply.setAction("message");
//        quickReply.setMessageText("네, 그렇습니다.");
//        template.setQuickReplies(List.of(quickReply));

        response.setTemplate(template);

        return response;
    }

}
