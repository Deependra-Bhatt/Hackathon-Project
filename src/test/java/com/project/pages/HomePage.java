package com.project.pages;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final Logger log = LogManager.getLogger(HomePage.class);

    // PageFactory Locators
    @FindBy(css = "ct-web-popup-imageonly")
    private WebElement shadowHost;

    @FindBy(xpath = "//div[@role='button' and @aria-label='Storage Furniture menu']")
    private WebElement storageFurnitureMenu;

    @FindBy(xpath = "//a[@class='njdyQ' and contains(@href, '/collection/bookshelves')]")
    private WebElement bookshelvesLink;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void handlePopUpIfPresent() {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("ct-web-popup-imageonly")));
            SearchContext shadowRoot = shadowHost.getShadowRoot();
            WebElement closeButton = shadowRoot.findElement(By.cssSelector("#close"));
            closeButton.click();
            log.info("Pop-up successfully closed!");
            Thread.sleep(1000);
        } catch (Exception e) {
            log.warn("Pop-up skipped or not found.");
        }
    }

    public void navigateToBookshelves() {
        wait.until(ExpectedConditions.visibilityOf(storageFurnitureMenu));
        Actions actions = new Actions(driver);
        actions.moveToElement(storageFurnitureMenu).perform();
        
        wait.until(ExpectedConditions.elementToBeClickable(bookshelvesLink)).click();
        log.info("Navigated to Bookshelves via Navigation Bar.");
    }
}