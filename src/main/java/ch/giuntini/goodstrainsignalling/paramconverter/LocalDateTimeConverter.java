package ch.giuntini.goodstrainsignalling.paramconverter;

import javax.ws.rs.ext.ParamConverter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LocalDateTimeConverter implements ParamConverter<LocalDateTime> {

    @Override
    public LocalDateTime fromString(String value) {
        if (value == null)
            return null;
        LocalDateTime ldt;
        try {
            ldt = LocalDateTime.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));;
        } catch (DateTimeParseException e) {
            return null;
        }
        return ldt;
    }

    @Override
    public String toString(LocalDateTime value) {
        if (value == null)
            return null;
        return value.toString();
    }
}