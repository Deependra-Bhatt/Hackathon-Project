package com.project.Hooks;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.project.Utils.ConfigReader;

import io.cucumber.java.After;

public class Hooks {

    public static WebDriver driver;
    private static final Logger log = LogManager.getLogger(Hooks.class);


    /**
     * Called from step definitions with the browser name
     * (fetched from config.properties)
     */
    public static void launchBrowser(String browser) {

        switch (browser.toLowerCase()) {

            case "firefox":
                driver = new FirefoxDriver();
                break;

            case "edge":
                driver = new EdgeDriver();
                break;

            case "chrome":
            default:
                driver = new ChromeDriver();
                break;
        }

        driver.manage().window().maximize();

        driver.manage().timeouts()
              .implicitlyWait(
                      Duration.ofSeconds(
                              ConfigReader.getImplicitWait()));

        log.info(
                "Browser [" + browser + "] launched successfully.");
    }

    /**
     * Returns the current driver instance
     */
    public static WebDriver getDriver() {
        return driver;
    }

    /**
     * Automatically closes the browser after each scenario
     */
    @After
    public void tearDown() {

        if (driver != null) {

            driver.quit();
            driver = null;

            log.info("Browser closed after scenario execution.");
        }
    }
}