package com.lsyh.seukseuk.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.DayOfWeek;
import java.util.Map;

@Entity
@Data
@EqualsAndHashCode(callSuper=false)
public class Employee extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer age;
    private String email;
    private String gender;
    private String tel;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "employee_working_days", joinColumns = @JoinColumn(name = "employee_id"))
    @MapKeyColumn(name = "day_of_week")
    @MapKeyEnumerated(EnumType.STRING)
    @Column(name = "wage")
    private Map<DayOfWeek, Integer> dailyWages;  // Map 구조로 각 요일별 일당을 저장
}
