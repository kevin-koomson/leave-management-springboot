package com.kevo.LeavesRemaster.utilites;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Service
@RequiredArgsConstructor
public class JsonProcessingService {
    private final ObjectMapper objectMapper;

    public <K> K processJsonFile(String jsonPayload, Class<K> valueType) throws JsonProcessingException {
        JsonNode dataNode = objectMapper.readTree(jsonPayload).path("data");
        return objectMapper.treeToValue(dataNode, valueType);
    }
    public <X> Class<?> convertDateFields(X object) throws IllegalAccessException {
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getFields();
        for(Field field : fields) {
            field.setAccessible(true);
            if(field.getType().equals(LocalDateTime.class)){
                LocalDateTime localDateTime = (LocalDateTime) field.get(clazz);
                if (localDateTime != null) {
                    OffsetDateTime offsetDateTime = localDateTime.atOffset(ZoneOffset.UTC);
                    field.set(clazz, offsetDateTime);
                }
            }
        }
        return clazz;
    }
}
