package com.project.pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;



public class CollectionsPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final JavascriptExecutor js;
    private static final Logger log = LogManager.getLogger(CollectionsPage.class);

    @FindBy(xpath = "//span[normalize-space()='New Arrivals']")
    private WebElement newArrivalsLink;

    public CollectionsPage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.js     = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
    }

    public void clickNewArrivals() {

        try {
            wait.until(
                    ExpectedConditions.elementToBeClickable(newArrivalsLink));

            js.executeScript("arguments[0].click();", newArrivalsLink);

            waitForPageLoad();

            System.out.println("Clicked New Arrivals");

        } catch (Exception e) {

            System.out.println("Unable to Click New Arrivals");
            e.printStackTrace();
        }
    }

    public void clickOasisCollection() {

        try {

            WebElement oasisLink =
                    wait.until(
                            ExpectedConditions.elementToBeClickable(
                                    By.xpath(
                                            "//a[contains(@href,'oasis')] | //a[contains(text(),'Oasis')]")));

            js.executeScript("arguments[0].scrollIntoView(true);", oasisLink);
            js.executeScript("arguments[0].click();",              oasisLink);

            waitForPageLoad();

            System.out.println("Clicked Oasis Collection");
            System.out.println("Current URL : " + driver.getCurrentUrl());

        } catch (Exception e) {

            System.out.println("Unable to Click Oasis Collection");
            e.printStackTrace();
        }
    }

    public List<String> getOasisSubMenuItems() {

        List<String> menuItems = new ArrayList<>();

        try {

            wait.until(
                    ExpectedConditions.presenceOfAllElementsLocatedBy(
                            By.tagName("a")));

            List<WebElement> links = driver.findElements(By.tagName("a"));

            for (WebElement link : links) {

                String text = link.getText().trim();

                if (!text.isEmpty() && !menuItems.contains(text)) {
                    menuItems.add(text);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return menuItems;
    }

    public void displayOasisSubMenuItems() {

        List<String> menuItems = getOasisSubMenuItems();

        //System.out.println();
        log.info("========================================");
        log.info(" OASIS COLLECTION SUBMENU ITEMS");
        log.info("========================================");

        for (int i = 0; i < menuItems.size(); i++) {
            System.out.println((i + 1) + ". " + menuItems.get(i));
        }

        System.out.println();
        log.info("Total Menu Items : " + menuItems.size());
        log.info("========================================");

        Assert.assertTrue(
                menuItems.size() > 0,
                "No Oasis submenu items found");
    }

    private void waitForPageLoad() {

        wait.until(driver ->
                ((JavascriptExecutor) driver)
                        .executeScript("return document.readyState")
                        .equals("complete"));
    }
}
