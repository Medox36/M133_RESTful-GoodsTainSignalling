package ch.giuntini.goodstrainsignalling.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

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
public class LocalDateTimeSerializer extends StdSerializer<LocalDateTime> {

    /**
     * constructor
     */
    protected LocalDateTimeSerializer() {
        super(LocalDateTime.class);
    }

    /**
     * serializes a LocalDateTime object
     *
     * @param localDateTime
     * @param jsonGenerator
     * @param serializerProvider
     * @throws IOException
     */
    @Override
    public void serialize(
            LocalDateTime localDateTime,
            JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider
    ) throws IOException {
        jsonGenerator.writeString(localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));
    }
}
