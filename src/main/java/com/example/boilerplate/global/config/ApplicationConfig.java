package com.example.boilerplate.global.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import io.github.resilience4j.timelimiter.TimeLimiterRegistry;
import java.time.Duration;
import javax.persistence.EntityManager;
import org.modelmapper.ModelMapper;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ApplicationConfig {
    @Bean
    public CircuitBreakerFactory circuitBreakerFactory(ThreadPoolTaskExecutor threadPoolTaskExecutor) {
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                                                                        .failureRateThreshold(50)
                                                                        .slidingWindowSize(100)
                                                                        .waitDurationInOpenState(Duration.ofSeconds(3))
                                                                        .build();
        TimeLimiterConfig timeLimiterConfig = TimeLimiterConfig.custom()
                                                               .timeoutDuration(Duration.ofSeconds(5))
                                                               .build();

        CircuitBreakerRegistry circuitBreakerRegistry = CircuitBreakerRegistry.of(circuitBreakerConfig);
        TimeLimiterRegistry timeLimiterRegistry = TimeLimiterRegistry.of(timeLimiterConfig);

        Resilience4JCircuitBreakerFactory factory = new Resilience4JCircuitBreakerFactory(circuitBreakerRegistry, timeLimiterRegistry, null);
        factory.configureExecutorService(threadPoolTaskExecutor.getThreadPoolExecutor());
        return factory;
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        return modelMapper;
    }

    @Bean
    public JPAQueryFactory jpaQueryFactory(EntityManager entityManager) {
        return new JPAQueryFactory(entityManager);
    }
}
