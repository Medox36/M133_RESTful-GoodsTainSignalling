package ch.giuntini.goodstrainsignalling.paramconverter;

import javax.ws.rs.ext.ParamConverter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Parameter-Converter for LocalDateTime
 *
 * @author Lorenzo Giuntini (Medox36)
 * @since 2022.06.13
 * @version 1.1
 */
public class LocalDateTimeConverter implements ParamConverter<LocalDateTime> {

    /**
     * converts a String into a LocalDateTime object with pattern "yyyy-MM-dd'T'HH:mm:ss"
     *
     * @param value to be parsed into LocalDateTime
     * @return al LocalDateTime object when matching pattern otherwise null
     */
    @Override
    public LocalDateTime fromString(String value) {
        if (value == null)
            return null;
        LocalDateTime ldt;
        try {
            ldt = LocalDateTime.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        } catch (DateTimeParseException e) {
            return null;
        }
        return ldt;
    }

    /**
     * converts a LocalDateTime into a String
     *
     * @param value the localDateTime to be converted
     * @return String of LocalDateTime, null if value is null
     */
    @Override
    public String toString(LocalDateTime value) {
        if (value == null)
            return null;
        return value.toString();
    }
}
