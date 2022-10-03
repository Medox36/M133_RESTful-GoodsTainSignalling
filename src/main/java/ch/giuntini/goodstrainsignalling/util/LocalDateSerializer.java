package ch.giuntini.goodstrainsignalling.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * custom Serializer for jackson-databind
 *
 * @author Lorenzo Giuntini (Medox36)
 * @since 2022.06.12
 * @version 1.0
 */
public class LocalDateSerializer extends StdSerializer<LocalDate> {

    /**
     * constructor
     */
    public LocalDateSerializer() {
        super(LocalDate.class);
    }

    /**
     * serializes a LocalDate object
     *
     * @param localDate
     * @param jsonGenerator
     * @param provider
     * @throws IOException
     */
    @Override
    public void serialize(LocalDate localDate, JsonGenerator jsonGenerator, SerializerProvider provider)
            throws IOException {
        jsonGenerator.writeString(localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }
}
