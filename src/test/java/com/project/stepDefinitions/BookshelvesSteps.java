package com.project.stepDefinitions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

import com.project.Hooks.Hooks;
import com.project.Utils.ConfigReader;
import com.project.pages.BookshelvesPage;
import com.project.pages.HomePage;

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
@Feature("Bookshelves Product Filtering")
@Story("User should be able to browse and filter bookshelves")
public class BookshelvesSteps {
    HomePage homePage;
    BookshelvesPage bookshelvesPage;
    private static final Logger log = LogManager.getLogger(BookshelvesSteps.class);


    @Given("User launches the website")
    @Step("Launch website on browser")
    public void user_launches_the_website() {
        //Read Browser from config file
    	String browser = ConfigReader.getBrowser();
    	//Allure.parameter("browser", browser);
    	
    	//Initialize driver via Hooks
    	Hooks.launchBrowser(browser);
    	
    	//Navigate to HomePage
    	Hooks.getDriver().get(ConfigReader.getHomePageUrl());
    	
    	//Initialize page objects
    	homePage = new HomePage(Hooks.getDriver());
    	bookshelvesPage = new BookshelvesPage(Hooks.getDriver());
    	
    	log.info("Navigated tp HomePage on browser: "+browser);
	}

    @When("User dismisses introductory popups on homepage")
    @Step("Dismiss introductory popups")
    public void user_dismisses_any_introductory_popups() {
        homePage.handlePopUpIfPresent();
    }

    @And("User navigates to the Bookshelves section via Storage Furniture menu")
    @Step("Navigate to Bookshelves section")
    public void user_navigates_to_bookshelves() {
        homePage.navigateToBookshelves();
    }

    @And("User applies the open storage type filter")
    @Step("Apply open storage type filter")
    @Severity(SeverityLevel.CRITICAL)
    public void user_applies_the_open_storage_type_filter() {
        // Clear any subsequent promotional popups before interacting with the filter UI
        homePage.handlePopUpIfPresent(); 
        bookshelvesPage.applyOpenStorageFilter();
    }

    @Then("The product results list should not be empty")
    @Step("Validate product results are not empty")
    @Severity(SeverityLevel.BLOCKER)
    public void the_product_results_list_should_not_be_empty() {
        int totalItems = bookshelvesPage.getProductCardsCount();
        
        // Assertion 1: Verify element list is not empty
        Assert.assertTrue(totalItems > 0, "TC01 Failed: Product card array returned empty!");
        log.info("TC01 Passed: Found " + totalItems + " product elements.");
        
        //Allure.parameter("Total Product Items", String.valueOf(totalItems));
    }

    @Then("The results should display up to 5 items and all displayed items must contain {string}")
    @Step("Validate top products contain keyword")
    public void the_results_should_display_items_and_contain_keyword(String keyword) {
        //Allure.parameter("keyword", keyword);
        // Executes Assertions 2, 3, and 4 inside your BookshelvesPage loop logic
        bookshelvesPage.validateTopProducts(5, 15000, keyword);
    }

    @After
    @Step("Cleanup: Close browser and teardown")
    public void tearDown() {
        // Automatically runs at the end of each scenario outline iteration to close the active browser
        Hooks.tearDown();
        log.info("Browser closed successfully after scenario execution.");
    }
}