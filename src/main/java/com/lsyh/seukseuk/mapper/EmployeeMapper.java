package com.lsyh.seukseuk.mapper;

import com.lsyh.seukseuk.domain.Employee;
import com.lsyh.seukseuk.dto.EmployeeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface EmployeeMapper {
    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    EmployeeDTO.EmployeeResponse employeeToEmployeeResponseDto(Employee employee);
    EmployeeDTO.EmployeeSalaryResponse employeeToEmployeeSalaryResponseDto(Employee employee);
}
