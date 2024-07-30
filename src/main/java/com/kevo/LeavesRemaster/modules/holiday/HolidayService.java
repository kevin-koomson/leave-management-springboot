package com.kevo.LeavesRemaster.modules.holiday;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HolidayService {
    private final HolidayRepository holidayRepository;

    public List<Holiday> saveHolidays(List<HolidayDTO> dtoList){
        return holidayRepository.saveAll(
                dtoList
                        .stream()
                        .map(HolidayDTO::createHoliday)
                        .toList());
    }
}
