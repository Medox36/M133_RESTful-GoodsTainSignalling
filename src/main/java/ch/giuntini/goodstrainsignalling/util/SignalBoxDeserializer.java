package ch.giuntini.goodstrainsignalling.util;

import ch.giuntini.goodstrainsignalling.model.SignalBox;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

/**
 * custom Deserializer for jackson-databind
 *
 * @author Lorenzo Giuntini (Medox36)
 * @since 2022.06.26
 * @version 1.0
 */
public class SignalBoxDeserializer extends StdDeserializer<SignalBox> {

    /**
     * constructor
     */
    public SignalBoxDeserializer() {
        super(SignalBox.class);
    }

    /**
     * deserializes a SignalBox object
     *
     * @param jsonParser
     * @param deserializationContext
     * @return
     * @throws IOException
     * @throws JacksonException
     */
    @Override
    public SignalBox deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String trackSection = node.get("trackSection").asText();
        int workingSignalmen = (Integer) node.get("workingSignalmen").numberValue();
        SignalBox signalBox = new SignalBox();
        signalBox.setTrackSection(trackSection);
        signalBox.setWorkingSignalmen(workingSignalmen);
        return signalBox;
    }
}
