package org.apache.fineract.portfolio.naaccount.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;
import java.time.LocalDate;

/**
 * <p>
 * User: Vigneshwara.Prakash Date: 02-12-2020 Time: 18:53
 * <p>
 * LocalDateDeserializer is to help with local date deserialization.
 */

public class LocalDateDeserializer extends StdDeserializer<LocalDate> {

    private static final long serialVersionUID = 1L;

    protected LocalDateDeserializer() {
        super(LocalDate.class);
    }

    @Override
    public LocalDate deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        return LocalDate.parse(jp.readValueAs(String.class));
    }

}
