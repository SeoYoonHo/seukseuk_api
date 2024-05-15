package com.lsyh.seukseuk.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

public class Util {
    /**
     * 특정 월에서 특정 요일의 날짜를 모두 가져옵니다.
     *
     * @param year      연도
     * @param month     월 (1-12)
     * @param dayOfWeeks 가져올 요일 리스트
     * @return 특정 월에서 해당 요일의 모든 날짜 목록
     */
    public static Map<DayOfWeek, List<LocalDate>> getDatesForDaysOfWeek(int year, int month, Set<DayOfWeek> dayOfWeeks) {
        Map<DayOfWeek, List<LocalDate>> result = new HashMap<>();

        // 주어진 연도와 월의 첫 번째 날
        LocalDate firstDayOfMonth = LocalDate.of(year, month, 1);

        // 주어진 연도와 월의 마지막 날
        LocalDate lastDayOfMonth = firstDayOfMonth.withDayOfMonth(firstDayOfMonth.lengthOfMonth());

        // 모든 DayOfWeek에 대해 처리
        for (DayOfWeek dayOfWeek : dayOfWeeks) {
            List<LocalDate> dates = new ArrayList<>();
            LocalDate currentDate = firstDayOfMonth;

            // 첫 번째 해당 요일을 찾기 위해 반복
            while (!currentDate.getDayOfWeek().equals(dayOfWeek)) {
                currentDate = currentDate.plusDays(1);
            }

            // 해당 요일의 모든 날짜를 찾기 위해 반복
            while (currentDate.isBefore(lastDayOfMonth) || currentDate.equals(lastDayOfMonth)) {
                dates.add(currentDate);
                currentDate = currentDate.plusWeeks(1); // 다음 주 같은 요일로 이동
            }

            result.put(dayOfWeek, dates);
        }

        return result;
    }

}
