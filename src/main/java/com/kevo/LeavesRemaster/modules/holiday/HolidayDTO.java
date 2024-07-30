package com.kevo.LeavesRemaster.modules.holiday;

import lombok.Getter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
public class HolidayDTO {
    private String name;
    private String country;
    private String date;
    public Holiday createHoliday(){
        return Holiday.builder()
                .name(name)
                .country(country)
                .startDay((LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE)).atStartOfDay())
                .build();
    }
}
