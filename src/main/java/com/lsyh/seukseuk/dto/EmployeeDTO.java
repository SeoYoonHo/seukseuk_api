package com.lsyh.seukseuk.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.util.Map;

public class EmployeeDTO {

    @Getter @Setter
    public static class EmployeeResponse {
        private String name;
        private String age;
        private String email;

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("이름 : ");
            sb.append(name);
            sb.append("\n");

            sb.append("나이 : ");
            sb.append(age);
            sb.append("\n");

            sb.append("이메일 : ");
            sb.append(email);

            return sb.toString();
        }
    }

    @Getter @Setter
    public static class EmployeeSalaryResponse {
        private String name;
        private String age;
        private String email;
        private Map<DayOfWeek, Integer> dailyWages;
        private Integer salary;

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("이름 : ");
            sb.append(name);
            sb.append("\n");

            sb.append("나이 : ");
            sb.append(age);
            sb.append("\n");

            sb.append("이메일 : ");
            sb.append(email);
            sb.append("\n");

            sb.append("급여 : ");
            sb.append(salary);

            return sb.toString();
        }
    }
}
