package ch.giuntini.goodstrainsignalling.util;

import javax.ws.rs.ext.ParamConverter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeConverter implements ParamConverter<LocalDateTime> {
    @Override
    public LocalDateTime fromString(String value) {
        if (value == null)
            return null;
        return LocalDateTime.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
    }

    @Override
    public String toString(LocalDateTime value) {
        if (value == null)
            return null;
        return value.toString();
    }
}