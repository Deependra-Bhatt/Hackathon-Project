package com.project.stepDefinitions;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

import com.project.Hooks.Hooks;
import com.project.Utils.ConfigReader;
import com.project.pages.StudyChairsPage;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StudyChairsSteps {

    private StudyChairsPage studyChairPage;
    private List<String> topThreeChairs;
    private static final Logger log = LogManager.getLogger(StudyChairsSteps.class);


    @Given("User navigates to Study Chairs page")
    public void user_navigates_to_study_chairs_page() {
    	//Read browser from config
    	String browser = ConfigReader.getBrowser();
    	
    	//Initialize driver via Hooks
    	Hooks.launchBrowser(browser);
    	
    	//Directly opening the StudyChairs URL
    	Hooks.getDriver().get(ConfigReader.getStudyChairsUrl());
    	
    	//Initialize page object
    	studyChairPage = new StudyChairsPage(Hooks.getDriver());
    	
    	log.info("Directly opened Study Chairs Page on browser:"+browser);
        
    }

    @When("User dismisses introductory popups on study chairs page")
    public void user_dismisses_any_introductory_popups() {
        studyChairPage.handlePopUpIfPresent();
    }


    @And("User sorts the study chairs by popularity")
    public void user_sorts_the_study_chairs_by_popularity() {

        studyChairPage.sortByPopularity();
    }

    @And("User stores the first three study chair names")
    public void user_stores_the_first_three_study_chair_names() {

        studyChairPage.fetchTopThreeStudyChairs();

        topThreeChairs =
                studyChairPage.getTopThreeChairNames();
    }

    @Then("Exactly three study chair names should be present")
    public void exactly_three_study_chair_names_should_be_present() {

        Assert.assertNotNull(topThreeChairs);

        Assert.assertEquals(
                topThreeChairs.size(),
                3,
                "Expected exactly 3 products.");
    }

    @Then("All fetched products should belong to study chairs category")
    public void all_fetched_products_should_belong_to_study_chairs_category() {

        Assert.assertNotNull(topThreeChairs);

        for (String chair : topThreeChairs) {

            Assert.assertFalse(
                    chair.isEmpty(),
                    "Chair name is empty");

            log.info(chair);
        }
    }
}

