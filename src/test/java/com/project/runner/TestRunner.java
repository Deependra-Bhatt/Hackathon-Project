package com.project.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
	features = {
			"src/test/resources/features/bookshelves.feature",
			    "src/test/resources/features/StudyChairs.feature",
			    "src/test/resources/features/giftCards.feature",
				"src/test/resources/features/Collections.feature"
			},
    glue = "com.project.stepDefinitions",
    plugin = {
        "pretty",
        "html:target/cucumber-reports/bdd-report.html",
        "json:target/cucumber-reports/cucumber.json",
        "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        "html:target/cucumber-reports/bdd-report.html",
        "com.project.Utils.CucumberExtentListener"
    },
    monochrome = true
)
public class TestRunner extends AbstractTestNGCucumberTests {
    // Left empty intentionally to inherit TestNG properties from runner architecture
}