package com.kminkov.payment.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.util.Optional;

@UtilityClass
public class ConvertionUtils {

    private static final String EMPTY_OBJECT = "{}";

    private static final ObjectMapper mapper = new ObjectMapper();

    public static <T> T fromJson(String json, Class<T> type) {
        try {
            return mapper.readValue(json, type);
        } catch (IOException exception) {
            throw new RuntimeException(exception.getMessage(), exception);
        }
    }

    public static String toJson(Object object) {
        return Optional.ofNullable(object)
                .map(ConvertionUtils::convertObject)
                .orElse(EMPTY_OBJECT);
    }

    private static String convertObject(@NonNull Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (IOException exception) {
            throw new RuntimeException(exception.getMessage(), exception);
        }
    }
}
