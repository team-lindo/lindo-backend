package team.lindo.backend.presentation.config.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import team.lindo.backend.common.extensions.objectMapper.ObjectMapperExtension;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class LindoAppConfiguration {
    @PersistenceContext
    private final EntityManager entityManager;

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        return ObjectMapperExtension.createGlobalObjectMapper();
    }

    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }
}
