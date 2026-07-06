package com.project.stepDefinitions;
 
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

import com.project.Hooks.Hooks;
import com.project.pages.GiftCardPage;

import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
 
public class GiftCardSteps {
 
    private static final Logger log = LogManager.getLogger(GiftCardSteps.class);

    private GiftCardPage giftCardPage;
 
    @Given("User launches the website on {string}")
    public void user_launches_the_website_on(String browser) {
        Hooks.launchBrowser(browser);

        // Initialize the page object now that the driver is alive
        giftCardPage = new GiftCardPage(Hooks.getDriver());
    }
    
    @And("User navigates to Gift Cards")
    public void user_navigates_to_gift_cards() {
        giftCardPage.navigateToGiftCardPage();
    }
 
    @When("User fills details")
    public void user_fills_details() {
        giftCardPage.enterAmount("5000");
        giftCardPage.enterQuantity("2");
        giftCardPage.selectAnniversaryCard();
        giftCardPage.scrollToForm();
        giftCardPage.fillGiftCardDetails();
        giftCardPage.scrollToPayButton();
        giftCardPage.clickPayNow();
    }
 
    @Then("Validation error message should be displayed")
    public void validation_error_message_should_be_displayed() {
        String actualError = giftCardPage.captureErrorMessage();
        log.info("Captured Error : {}", actualError);
 
        try {
        	giftCardPage.takeScreenshot("validation_error_message_should_be_displayed");
//            log.info("Screenshot saved at : {}");
        } catch (Exception e) {
            log.error("Failed to take screenshot", e);
        }
        
        Assert.assertEquals(actualError, "Enter valid Email ID.");
    }
    
    @After
    public void tearDown() {
        // Automatically runs at the end of each scenario outline iteration to close the active browser
        Hooks.tearDown();
        log.info("Browser closed successfully after scenario execution.");
    }
}
 