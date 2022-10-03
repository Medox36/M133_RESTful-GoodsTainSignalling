package ch.giuntini.goodstrainsignalling.paramconverter;

import javax.ws.rs.ext.ParamConverter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Parameter-Converter for LocalDate
 *
 * @author Lorenzo Giuntini (Medox36)
 * @since 2022.06.13
 * @version 1.1
 */
public class LocalDateConverter implements ParamConverter<LocalDate> {

    /**
     * converts a String into a LocalDate objects with pattern "yyyy-MM-dd"
     *
     * @param value to be parsed into LocalDate
     * @return a LocalDate object when matching the pattern otherwise null
     */
    @Override
    public LocalDate fromString(String value) {
        if (value == null)
            return null;
        LocalDate ld;
        try {
            ld = LocalDate.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (DateTimeParseException e) {
            return null;
        }
        return ld;
    }

    /**
     * converts a LocalDate into a String
     *
     * @param value the localDate to be converted
     * @return String of LocalDate, null if value is null
     */
    @Override
    public String toString(LocalDate value) {
        if (value == null)
            return null;
        return value.toString();
    }
}
