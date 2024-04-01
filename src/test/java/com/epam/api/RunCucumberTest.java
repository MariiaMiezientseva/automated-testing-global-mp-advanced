package com.epam.api;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.Test;

@CucumberOptions(
        features = {"src/test/resources/features/UserFilterJourney.feature"},
        glue = {"stepdefs"},
        plugin = {"pretty", "html:target/cucumber-report-seq-test.html"},
        monochrome = true
)
public class RunCucumberTest extends AbstractTestNGCucumberTests {

    @Test
    public void runScenario() {
    }
}
