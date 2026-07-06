package com.project.stepDefinitions;

import com.project.Hooks.Hooks;
import com.project.pages.CollectionsPage;
import com.project.pages.HomePage;
import com.project.Utils.ConfigReader;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CollectionsSteps {

    private HomePage homePage;
    private CollectionsPage collectionsPage;

    @Given("User launches Collections page")
    public void user_launches_collections_page() {

        // 1. Read browser from config
        String browser = ConfigReader.getBrowser();

        // 2. Initialize driver via Hooks
        Hooks.launchBrowser(browser);

        // 3. Navigate to HomePage
        Hooks.getDriver()
             .get(ConfigReader.getHomePageUrl());

        // 4. Initialize page objects
        homePage        = new HomePage(Hooks.getDriver());
        collectionsPage = new CollectionsPage(Hooks.getDriver());

        System.out.println(
                "Navigated to HomePage on browser: " + browser);
    }

    @When("User closes popup if present")
    public void user_closes_popup_if_present() {

        homePage.handlePopUpIfPresent();
    }

    @And("User navigates to New Arrivals")
    public void user_navigates_to_new_arrivals() {

        collectionsPage.clickNewArrivals();
    }

    @And("User opens Oasis Collection")
    public void user_opens_oasis_collection() {

        collectionsPage.clickOasisCollection();
    }

    @Then("Display Oasis submenu items")
    public void display_oasis_submenu_items() {

        collectionsPage.displayOasisSubMenuItems();
    }

    
}
