package com.project.stepDefinitions;
 
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.project.Hooks.Hooks;
import com.project.pages.GiftCardPage;

import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
 
@Epic("Urban Ladder - E-Commerce Platform")
@Feature("Gift Card Purchases")
@Story("User should validate gift card purchase with error handling")
public class GiftCardSteps {
 
    private static final Logger log = LogManager.getLogger(GiftCardSteps.class);

    private GiftCardPage giftCardPage;
 
    @Given("User launches the website on {string}")
    @Step("Launching browser with Urban Ladder website")
    public void user_launches_the_website_on(String browser) {
       // Allure.parameter("browser", browser);
        Hooks.launchBrowser(browser);

        // Initialize the page object now that the driver is alive
        giftCardPage = new GiftCardPage(Hooks.getDriver());
    }
    
    @And("User navigates to Gift Cards")
    @Step("Navigate to Gift Cards page")
    public void user_navigates_to_gift_cards() {
        giftCardPage.navigateToGiftCardPage();
    }
 
    @When("User fills details")
    @Step("Filling gift card details - Amount: 5000, Quantity: 2")
    @Severity(SeverityLevel.CRITICAL)
    public void user_fills_details() {
        Allure.step("Enter Amount: 5000", step -> {
            giftCardPage.enterAmount("5000");
        });
        
        Allure.step("Enter Quantity: 2", step -> {
            giftCardPage.enterQuantity("2");
        });
        
        Allure.step("Select Anniversary Card design", step -> {
            giftCardPage.selectAnniversaryCard();
        });
        
        Allure.step("Scroll to Gift Card Form", step -> {
            giftCardPage.scrollToForm();
        });
        
        Allure.step("Fill recipient details", step -> {
            giftCardPage.fillGiftCardDetails();
        });
        
        Allure.step("Scroll to Payment button", step -> {
            giftCardPage.scrollToPayButton();
        });
        
        Allure.step("Click Pay Now button", step -> {
            giftCardPage.clickPayNow();
        });
    }
 
    @Then("Validation error message should be displayed")
    @Step("Verify validation error message is displayed")
    @Severity(SeverityLevel.BLOCKER)
    public void validation_error_message_should_be_displayed() {
        String actualError = giftCardPage.captureErrorMessage();
        log.info("Captured Error : {}", actualError);
        Allure.step("Verifying error message for invalid email in gift card purchase");
 
        try {
        	giftCardPage.takeScreenshot("validation_error_message_should_be_displayed");
        	Allure.addAttachment(
        		    "Error Validation Screenshot",
        		    new ByteArrayInputStream(
        		        Files.readAllBytes(
        		            Paths.get(
        		                "Screenshots/validation_error_message_should_be_displayed.png"
        		            )
        		        )
        		    )
        		);
//            log.info("Screenshot saved at : {}");
        } catch (Exception e) {
            log.error("Failed to take screenshot", e);
            Allure.addAttachment("Error Details", "text/plain", e.getMessage());
        }
        
        //Allure.parameter("Expected Error", "Enter valid Email ID.");
        //Allure.parameter("Actual Error", actualError);
        
        Assert.assertEquals(actualError, "Enter valid Email ID.");
    }
    
    @After
    @Step("Cleanup: Close browser and teardown")
    public void tearDown() {
        // Automatically runs at the end of each scenario outline iteration to close the active browser
        Hooks.tearDown();
        log.info("Browser closed successfully after scenario execution.");
    }
}
 