package cz.gov.monitor.mfcr.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import cz.gov.monitor.mfcr.model.OrganizationType;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OrganizationTypeDeserializer extends StdDeserializer<OrganizationType> {

    public OrganizationTypeDeserializer() {
        this(null);
    }

    public OrganizationTypeDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public OrganizationType deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String textToParse = jsonParser.getText();

        return OrganizationType.UNKNOWN;
    }


}
