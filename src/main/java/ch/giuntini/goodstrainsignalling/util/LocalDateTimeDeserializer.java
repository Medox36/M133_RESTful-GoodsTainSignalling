package ch.giuntini.goodstrainsignalling.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * custom Deserializer for jackson-databind
 *
 * @author Lorenzo Giuntini (Medox36)
 * @since 2022.06.12
 * @version 1.0
 */
public class LocalDateTimeDeserializer extends StdDeserializer<LocalDateTime> {

    /**
     * constructor
     */
    protected LocalDateTimeDeserializer() {
        super(LocalDateTime.class);
    }

    /**
     * converts data from String to LocalDateTime
     *
     * @param jsonParser
     * @param deserializationContext
     * @return LocalDateTime
     * @throws IOException
     */
    @Override
    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        String value = jsonParser.readValueAs(String.class);
        return LocalDateTime.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
    }
}