package com.kevo.LeavesRemaster.modules.holiday;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.List;

@DgsComponent
@RequiredArgsConstructor
public class HolidayController {
    private final HolidayAPI holidayAPI;
    private final HolidayService holidayService;

    @DgsQuery
    List<HolidayDTO> generateHolidays(@InputArgument String country, @InputArgument String year) throws IOException, InterruptedException {
        return holidayAPI.getHolidaysFromAPI(year, country);
    }
    @DgsMutation
    List<Holiday> saveHolidays(@InputArgument List<HolidayDTO> data) throws IOException, InterruptedException {
        return holidayService.saveHolidays(data);
    }
}
