package com.lsyh.seukseuk.domain;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Getter @Setter @ToString
@EqualsAndHashCode(callSuper=false)
@Table(name = "employee_working_day", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"employee_id", "date"})
})
public class EmployeeWorkingDay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(nullable = false)
    private LocalDate date;
    private boolean isWorking;


}
