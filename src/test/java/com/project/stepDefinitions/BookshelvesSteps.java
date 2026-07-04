package com.project.stepDefinitions;

import com.project.Hooks.Hooks;
import com.project.Utils.ConfigReader;
import com.project.base.BaseTest;
import com.project.pages.BookshelvesPage;
import com.project.pages.HomePage;
import io.cucumber.java.After;
import io.cucumber.java.en.*;
import org.testng.Assert;

public class BookshelvesSteps {
    HomePage homePage;
    BookshelvesPage bookshelvesPage;

    @Given("User launches the website")
    public void user_launches_the_website() {
        //Read Browser from config file
    	String browser = ConfigReader.getBrowser();
    	
    	//Initialize driver via Hooks
    	Hooks.launchBrowser(browser);
    	
    	//Navigate to HomePage
    	Hooks.getDriver().get(ConfigReader.getHomePageUrl());
    	
    	//Initialize page objects
    	homePage = new HomePage(Hooks.getDriver());
    	bookshelvesPage = new BookshelvesPage(Hooks.getDriver());
    	
    	System.out.println("Navigated tp HomePage on browser: "+browser);
	}

    @When("User dismisses introductory popups on homepage")
    public void user_dismisses_any_introductory_popups() {
        homePage.handlePopUpIfPresent();
    }

    @And("User navigates to the Bookshelves section via Storage Furniture menu")
    public void user_navigates_to_bookshelves() {
        homePage.navigateToBookshelves();
    }

    @And("User applies the open storage type filter")
    public void user_applies_the_open_storage_type_filter() {
        // Clear any subsequent promotional popups before interacting with the filter UI
        homePage.handlePopUpIfPresent(); 
        bookshelvesPage.applyOpenStorageFilter();
    }

    @Then("The product results list should not be empty")
    public void the_product_results_list_should_not_be_empty() {
        int totalItems = bookshelvesPage.getProductCardsCount();
        
        // Assertion 1: Verify element list is not empty
        Assert.assertTrue(totalItems > 0, "TC01 Failed: Product card array returned empty!");
        System.out.println("TC01 Passed: Found " + totalItems + " product elements.");
    }

    @Then("The results should display up to 5 items and all displayed items must contain {string}")
    public void the_results_should_display_items_and_contain_keyword(String keyword) {
        // Executes Assertions 2, 3, and 4 inside your BookshelvesPage loop logic
        bookshelvesPage.validateTopProducts(5, 15000, keyword);
    }

   /* @After
    public void tearDown() {
        // Automatically runs at the end of each scenario outline iteration to close the active browser
        BaseTest.quitDriver();
        System.out.println("Browser closed successfully after scenario execution.");
    }*/
}