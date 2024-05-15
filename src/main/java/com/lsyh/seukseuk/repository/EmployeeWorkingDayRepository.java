package com.lsyh.seukseuk.repository;

import com.lsyh.seukseuk.domain.EmployeeWorkingDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface EmployeeWorkingDayRepository extends JpaRepository<EmployeeWorkingDay, Long> {

    @Query("SELECT ewd FROM EmployeeWorkingDay ewd WHERE ewd.employee.id = :employeeId AND ewd.date BETWEEN :startDate AND :endDate")
    List<EmployeeWorkingDay> findEmployeeWorkingDaysByEmployeeAndDateRange(@Param("employeeId") Long employeeId,
                                                                           @Param("startDate") LocalDate startDate,
                                                                           @Param("endDate") LocalDate endDate);
}
