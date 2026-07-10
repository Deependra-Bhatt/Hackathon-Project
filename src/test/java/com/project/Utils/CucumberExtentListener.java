package com.project.Utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.project.Hooks.Hooks;
import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.*;

import java.util.Date;

import org.openqa.selenium.WebDriver;

public class CucumberExtentListener implements ConcurrentEventListener {

    private static final ExtentReports extent = ExtentManager.createInstance();
    
    private static final ThreadLocal<ExtentTest> featureTest = new ThreadLocal<>();
    private static final ThreadLocal<ExtentTest> scenarioTest = new ThreadLocal<>();
    private static final ThreadLocal<ExtentTest> stepTest = new ThreadLocal<>();

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestSourceRead.class, this::handleSourceRead);
        publisher.registerHandlerFor(TestCaseStarted.class, this::handleTestCaseStarted);
        publisher.registerHandlerFor(TestStepStarted.class, this::handleTestStepStarted);
        publisher.registerHandlerFor(TestStepFinished.class, this::handleTestStepFinished);
        publisher.registerHandlerFor(TestRunFinished.class, this::handleTestRunFinished);
    }

    private void handleSourceRead(TestSourceRead event) {
        String featureName = event.getUri().toString();
        featureName = featureName.substring(featureName.lastIndexOf("/") + 1);
        featureTest.set(extent.createTest(featureName));
    }

    private void handleTestCaseStarted(TestCaseStarted event) {
        ExtentTest scenario = featureTest.get().createNode(event.getTestCase().getName());
        scenarioTest.set(scenario);
    }

    private void handleTestStepStarted(TestStepStarted event) {
        TestStep step = event.getTestStep();
        
        // This filters out automatic background hooks (Before/After actions) 
        // so that ONLY literal Gherkin steps are written to your report.
        if (step instanceof PickleStepTestStep) {
            PickleStepTestStep pickleStep = (PickleStepTestStep) step;
            ExtentTest stepNode = scenarioTest.get().createNode(pickleStep.getStep().getText());
            stepTest.set(stepNode);
        } else if (step.getClass().getName().contains("Pickle")) {
            // Fallback reflection catch-all for intermediate Cucumber versions
            try {
                java.lang.reflect.Method getStepMethod = step.getClass().getMethod("getStep");
                Object stepObj = getStepMethod.invoke(step);
                java.lang.reflect.Method getTextMethod = stepObj.getClass().getMethod("getText");
                String stepText = (String) getTextMethod.invoke(stepObj);
                
                ExtentTest stepNode = scenarioTest.get().createNode(stepText);
                stepTest.set(stepNode);
            } catch (Exception e) {
                // Ignore background hooks quietly
            }
        }
    }

    private void handleTestStepFinished(TestStepFinished event) {
    	Date date = new Date();
        // Only log actual test steps, skip background lifecycle hooks
        String stepClassName = event.getTestStep().getClass().getName();
        if (stepClassName.contains("Pickle") || stepClassName.contains("Step")) {
            
            io.cucumber.plugin.event.Status cucumberStatus = event.getResult().getStatus();
            com.aventstack.extentreports.Status extentStatus = convertStatus(cucumberStatus);
            
            if (stepTest.get() != null) {
                if (extentStatus == com.aventstack.extentreports.Status.FAIL) {
                    stepTest.get().log(com.aventstack.extentreports.Status.FAIL, "Step Failed: " + event.getResult().getError());
                    
                    try {
                        WebDriver driver = Hooks.getDriver();
                        if (driver != null) {
                            String screenshotName = "StepFailure_" + System.currentTimeMillis();
                            ScreenshotUtil.capture(driver, screenshotName);
                            stepTest.get().addScreenCaptureFromPath("../Screenshots/" + screenshotName + date.getDate()+ date.getTime() + ".png");
                        }
                    } catch (Exception e) {
                        stepTest.get().log(com.aventstack.extentreports.Status.WARNING, "Could not attach screenshot: " + e.getMessage());
                    }
                } else {
                    stepTest.get().log(extentStatus, "Step completed with status: " + extentStatus);
                }
            }
        }
    }

    private void handleTestRunFinished(TestRunFinished event) {
        extent.flush();
    }

    private com.aventstack.extentreports.Status convertStatus(io.cucumber.plugin.event.Status cucumberStatus) {
        switch (cucumberStatus) {
            case PASSED: 
                return com.aventstack.extentreports.Status.PASS;
            case FAILED: 
                return com.aventstack.extentreports.Status.FAIL;
            case SKIPPED: 
                return com.aventstack.extentreports.Status.SKIP;
            case PENDING:
            case UNDEFINED: 
                return com.aventstack.extentreports.Status.WARNING;
            default: 
                return com.aventstack.extentreports.Status.INFO;
        }
    }
}