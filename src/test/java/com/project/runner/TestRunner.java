package com.project.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
	features = {"src/test/resources/features/bookshelves.feature",
			    "src/test/resources/features/StudyChairs.feature"
			},
    glue = "com.project.stepDefinitions",
    plugin = {
        "pretty",
        "html:target/cucumber-reports/bdd-report.html"
    },
    monochrome = true
)
public class TestRunner extends AbstractTestNGCucumberTests {
    // Left empty intentionally to inherit TestNG properties from runner architecture
}