package com.lsyh.seukseuk.domain;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
@Getter @Setter
@EqualsAndHashCode(callSuper=false)
public class Employee extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Long id;
    private String name;
    private Integer age;
    private String email;
    private String gender;
    private String tel;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<EmployeeWorkingDay> workingDayList = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "employee_working_day_of_weeks", joinColumns = @JoinColumn(name = "employee_id"))
    @MapKeyColumn(name = "day_of_week")
    @MapKeyEnumerated(EnumType.STRING)
    @Column(name = "wage")
    private Map<DayOfWeek, Integer> dailyWages;  // Map 구조로 각 요일별 일당을 저장
}
