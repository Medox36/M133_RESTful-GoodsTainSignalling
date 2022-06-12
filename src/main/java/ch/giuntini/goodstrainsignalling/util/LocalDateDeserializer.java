package ch.giuntini.goodstrainsignalling.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * custom Deserializer for jackson-databind
 */
public class LocalDateDeserializer extends StdDeserializer<LocalDate> {
    /**
     * constructor
     */
    protected LocalDateDeserializer() {
        super(LocalDate.class);
    }

    /**
     * converts data from String to LocalDate
     *
     * @param jsonParser
     * @param context
     * @return
     * @throws IOException
     */
    @Override
    public LocalDate deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
        String value = jsonParser.readValueAs(String.class);
        return LocalDate.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}