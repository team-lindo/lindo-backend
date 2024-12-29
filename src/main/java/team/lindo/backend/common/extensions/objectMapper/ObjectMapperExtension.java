package team.lindo.backend.common.extensions.objectMapper;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

public class ObjectMapperExtension {
    public static ObjectMapper createGlobalObjectMapper() {
        return new Jackson2ObjectMapperBuilder()
                .build()
                .configure(DeserializationFeature.USE_LONG_FOR_INTS, true)
                .configure(DeserializationFeature.USE_LONG_FOR_INTS, true)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true)
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE)
                .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    }
}
