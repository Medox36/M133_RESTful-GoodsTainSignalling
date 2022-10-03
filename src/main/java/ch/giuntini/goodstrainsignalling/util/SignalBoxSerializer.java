package ch.giuntini.goodstrainsignalling.util;

import ch.giuntini.goodstrainsignalling.model.SignalBox;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * custom Serializer for jackson-databind
 *
 * @author Lorenzo Giuntini (Medox36)
 * @since 2022.06.26
 * @version 1.0
 */
public class SignalBoxSerializer extends StdSerializer<SignalBox> {

    /**
     * constructor
     */
    public SignalBoxSerializer() {
        super(SignalBox.class);
    }

    /**
     * serializes a SignalBox object
     *
     * @param signalBox
     * @param jsonGenerator
     * @param serializerProvider
     * @throws IOException
     */
    @Override
    public void serialize(SignalBox signalBox, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("trackSection", signalBox.getTrackSection());
        jsonGenerator.writeNumberField("workingSignalmen", signalBox.getWorkingSignalmen());
        jsonGenerator.writeEndObject();
    }
}
