package com.aaron.challenge.flightadvisor.cucumber;

import com.aaron.challenge.flightadvisor.FlightAdvisorApplication;
import com.aaron.challenge.flightadvisor.cucumber.config.DisableTestSecurityConfig;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {FlightAdvisorApplication.class, DisableTestSecurityConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = SpringConfiguration.Initializer.class)
public abstract class SpringConfiguration {

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            if (CucumberTest.postgreSQLContainer.isRunning()) {
                TestPropertyValues.of(
                        "spring.datasource.url=" + CucumberTest.postgreSQLContainer.getJdbcUrl(),
                        "spring.datasource.username=" + CucumberTest.postgreSQLContainer.getUsername(),
                        "spring.datasource.password=" + CucumberTest.postgreSQLContainer.getPassword(),
                        "spring.datasource.platform=POSTGRESQL",
                        "spring.flyway.enabled=true"
                ).applyTo(configurableApplicationContext.getEnvironment());
            }
        }

    }

}
