package com.epam.api;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = {"src/test/resources/features/UserFiltersParallelUpdates.feature"},
        glue = {"stepdefs"},
        plugin = {"pretty", "html:target/cucumber-report-parallel-test.html"},
        monochrome = true
)
public class RunCucumberParallelTest extends AbstractTestNGCucumberTests {
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
