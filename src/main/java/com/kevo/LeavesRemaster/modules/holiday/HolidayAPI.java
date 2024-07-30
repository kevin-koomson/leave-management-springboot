package com.kevo.LeavesRemaster.modules.holiday;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HolidayAPI {
    private final ObjectMapper objectMapper;
    @Value("${api.holiday.uri}")
    String uri;
    @Value("${api.holiday.key}")
    String key;
    String holidayType = "&type=public_holiday";

    HttpClient client = HttpClient.newHttpClient();

    public List<HolidayDTO> getHolidaysFromAPI(String year, String country) throws IOException, InterruptedException {
        year = "&year=" + year;
        country = "&country=" + country;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri + country + year + holidayType))
                .header("X-Api-Key", key)
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if(response.statusCode() != 200 || response.body().isBlank()) {
            String error = objectMapper.readTree(response.body()).get("error").asText();
            throw new InterruptedException("Error fetching holidays: " + error);
        }
        return parseHolidays(response.body());
    }
    public List<HolidayDTO> parseHolidays(String json) throws JsonProcessingException {
        List<HolidayDTO> holidays = new ArrayList<>();
        JsonNode array = objectMapper.readTree(json);
        if(array.isArray()) {
            for(JsonNode node: array) {
                holidays.add(objectMapper.convertValue(node, HolidayDTO.class));
            }
        }
        return holidays;
    }
}
