package com.lsyh.seukseuk.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

public class HolidayDTO {

    @Getter @Setter @ToString
    public static class HolidayRequest {
        private Integer year;
        private Integer month;
    }

    @Getter @Setter
    public static class HolidayResponse {
        private LocalDate date;
        private String description;
    }
}
