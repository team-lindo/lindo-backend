package team.lindo.backend.presentation.config.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import team.lindo.backend.common.extensions.objectMapper.ObjectMapperExtension;

@Configuration
@EnableWebSecurity
public class LindoAppConfiguration {
    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        return ObjectMapperExtension.createGlobalObjectMapper();
    }
}
