package com.project.stepDefinitions;

import com.project.Hooks.Hooks;
import com.project.pages.CollectionsPage;
import com.project.pages.HomePage;
import com.project.Utils.ConfigReader;

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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Epic("Urban Ladder - E-Commerce Platform")
@Feature("Collections and New Arrivals")
@Story("User should be able to browse collections and new arrivals")
public class CollectionsSteps {

    private HomePage homePage;
    private CollectionsPage collectionsPage;
    private static final Logger log = LogManager.getLogger(CollectionsSteps.class);

    @Given("User launches Collections page")
    @Step("Launch Collections page on")
    public void user_launches_collections_page() {
        // 1. Read browser from config
        String browser = ConfigReader.getBrowser();
        //Allure.parameter("browser", browser);

        // 2. Initialize driver via Hooks
        Hooks.launchBrowser(browser);

        // 3. Navigate to HomePage
        Hooks.getDriver().get(ConfigReader.getHomePageUrl());

        // 4. Initialize page objects
        homePage        = new HomePage(Hooks.getDriver());
        collectionsPage = new CollectionsPage(Hooks.getDriver());

        log.info("Navigated to HomePage on browser: " + browser);
    }

    @When("User closes popup if present")
    @Step("Close popup if present on page")
    public void user_closes_popup_if_present() {
        homePage.handlePopUpIfPresent();
    }

    @And("User navigates to New Arrivals")
    @Step("Navigate to New Arrivals section")
    @Severity(SeverityLevel.CRITICAL)
    public void user_navigates_to_new_arrivals() {
        collectionsPage.clickNewArrivals();
    }

    @Then("Display Oasis submenu items")
    @Step("Display Oasis submenu items")
    @Severity(SeverityLevel.BLOCKER)
    public void display_oasis_submenu_items() {
        collectionsPage.displayOasisSubMenuItems();
        Allure.description("Oasis submenu items have been displayed");
    }

    @After
    @Step("Cleanup: Close browser and teardown")
    public void tearDown() {
        Hooks.tearDown();
        log.info("Browser closed successfully after scenario execution.");
    }
}

