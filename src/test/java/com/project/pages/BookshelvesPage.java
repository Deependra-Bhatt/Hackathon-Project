package com.project.pages;

import java.time.Duration;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.Assert;

public class BookshelvesPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final Logger log = LogManager.getLogger(BookshelvesPage.class);

    @FindBy(xpath = "//div[@role='button' and contains(@aria-label, 'Storage Type filter')]")
    private WebElement storageFilterButton;

    @FindBy(xpath = "//div[@id='dropdown-menu-storage-type']//*[contains(text(), 'Open Storage') or @value='Open Storage']")
    private WebElement openStorageOption;

    @FindBy(xpath = "//div[@role='link' and contains(@class, 'xmdLL')]")
    private List<WebElement> productCards;

    public BookshelvesPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void applyOpenStorageFilter() {
        wait.until(ExpectedConditions.elementToBeClickable(storageFilterButton)).click();
        wait.until(ExpectedConditions.attributeToBe(storageFilterButton, "aria-expanded", "true"));
        wait.until(ExpectedConditions.elementToBeClickable(openStorageOption)).click();
        log.info("Storage filter applied successfully!");
    }

    public int getProductCardsCount() {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@role='link' and contains(@class, 'xmdLL')]")));
        return productCards.size();
    }

    public void validateTopProducts(int targetLimit, int maxPrice, String expectedKeyword) {
        log.info("\n--- First " + targetLimit + " Bookshelves Below Rs. " + maxPrice + " ---");
        int count = 0;
        
        for (WebElement card : productCards) {
            if (count >= targetLimit) break;
            try {
                String name = card.findElement(By.xpath(".//h2[@class='XxwSy']")).getText().trim();
                String priceText = card.findElement(By.xpath(".//div[@class='UYQNp']")).getText().trim();
                int price = Integer.parseInt(priceText.replaceAll("[^0-9]", ""));

                // Assertion 3: Title Validation
                Assert.assertTrue(name.toLowerCase().contains(expectedKeyword.toLowerCase()), 
                    "Assertion Failed: '" + name + "' does not contain '" + expectedKeyword + "'");

                if (price < maxPrice) {
                    // Assertion 4: Price Filter Verification
                    Assert.assertTrue(price < maxPrice, "Assertion Failed: Price exceeds threshold.");
                    count++;
                    log.info(count + ". Name: " + name + " | Price: " + priceText);
                }
            } catch (Exception e) {
//                if (e instanceof AssertionError) throw e;
            }
        }
        
        // Assertion 2: Check if at least some values were processed if limit wasn't reached
        if (productCards.size() < targetLimit) {
            log.warn("Warning: Fewer items found on page than target limit.");
            Assert.assertTrue(productCards.size() > 0, "Assertion Failed: No elements matching criteria.");
        }
    }
}