package org.apache.fineract.portfolio.naaccount.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

public class LocalDateSerializer extends JsonSerializer<LocalDate> {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void serialize(LocalDate value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {

        String formattedDate = dateFormat.format(value);
        jgen.writeString(formattedDate);
    }

}
