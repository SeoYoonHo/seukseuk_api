package com.lsyh.seukseuk.service;

import com.lsyh.seukseuk.repository.EmployeeRepository;
import com.lsyh.seukseuk.repository.HolidayRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    EmployeeRepository employeeRepository;
    @Mock
    HolidayRepository holidayRepository;

    EmployeeService employeeService;

    @Test
    public void givenValidEmployeeName_whenCalculateSalary_thenSalary() {
        // given
        String validEmployeeName = "Valid Employee Name";

        //when
//        Throwable throwable = assertThrows(NoSearchException.class, () -> authService.login(memberDto));

        // then
    }
}