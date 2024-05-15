package com.lsyh.seukseuk.repository;

import com.lsyh.seukseuk.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{
    Optional<Employee> findEmployeeByName(String employeeName);
}
