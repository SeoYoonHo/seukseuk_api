package com.lsyh.seukseuk.repository;

import com.lsyh.seukseuk.domain.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.List;

public interface HolidayRepository extends JpaRepository<Holiday, Long>{
    @Query("SELECT h FROM Holiday h WHERE FUNCTION('MONTH', h.date) = :month AND FUNCTION('YEAR', h.date) = :year")
    List<Holiday> findHolidaysByMonthAndYear(int month, int year);

    List<Holiday> findByDateBetween(LocalDate startDate, LocalDate endDate);
}
