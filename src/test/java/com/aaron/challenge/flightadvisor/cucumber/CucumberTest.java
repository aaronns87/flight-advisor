package com.aaron.challenge.flightadvisor.cucumber;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.testcontainers.containers.PostgreSQLContainer;

import java.time.Duration;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features/", plugin = {"json:build/cucumber-output/advisor/cucumber.json"})
public class CucumberTest {

    @ClassRule
    public static final PostgreSQLContainer postgreSQLContainer = (PostgreSQLContainer) new PostgreSQLContainer("postgres:13.0")
            .withDatabaseName("advisor")
            .withUsername("postgres")
            .withPassword("postgres")
            .withStartupTimeout(Duration.ofSeconds(600));

}
