package com.project.giftcard;

import java.io.File;
import java.time.Duration;
import java.util.ArrayList; // Added for correct window handle conversion
import java.util.List;
import java.util.Scanner;
import java.util.Set; // Added for getWindowHandles()

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GiftCards {
	static WebDriver driver = null;
	String baseUrl = "https://www.urbanladder.com/";

	// Function to get driver based on user input
	public WebDriver getDriverFromUser(String driverInput) throws Exception {
		if (driverInput.equalsIgnoreCase("Chrome")) {
			driver = new ChromeDriver();
		} else if (driverInput.equalsIgnoreCase("Edge")) {
			driver = new EdgeDriver();
		} else if (driverInput.equalsIgnoreCase("Firefox")) {
			driver = new FirefoxDriver();
		} else {
			throw new Exception("Invalid Browser");
		}

		driver.get(baseUrl);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.manage().window().maximize();
		return driver;
	}

	public void handleShadowDOMAlert() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			WebElement shadowHost = wait.until(
					ExpectedConditions.presenceOfElementLocated(By.cssSelector("ct-web-popup-imageonly"))
					);

			SearchContext shadowRoot = shadowHost.getShadowRoot();
			WebElement closeButton = shadowRoot.findElement(By.cssSelector("#close"));
			closeButton.click();
			System.out.println("Pop-up successfully closed!");

			// Give the UI a brief split-second to remove the overlay backdrop
			Thread.sleep(1000); 
		} catch (Exception e) {
			System.err.println("Pop-up skipped or not found: " + e.getMessage());
		}
	}

	public void openGiftCardsPage() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		// Fixed XPath: Target the div directly using its unique aria-label attribute
		WebElement giftCardsLink = wait.until(
				ExpectedConditions.visibilityOfElementLocated(
						By.xpath("//a[@class='ETrkE' and contains(text(),'Gift Cards')]")
						)
				);
		giftCardsLink.click();

		System.out.println("Successfully clicked Gift Cards link.");
	}
	
	// FIX: Safely converted Set to List to prevent ClassCastException
	public void changeWindowHandle() {
		Set<String> windowSet = driver.getWindowHandles();
		List<String> windowHandles = new ArrayList<>(windowSet);
		
		// Only switch if a second window/tab actually exists
		if (windowHandles.size() > 1) {
			driver.switchTo().window(windowHandles.get(1));
			System.out.println("Switched to the new window/tab.");
		} else {
			System.out.println("No new window detected. Staying on the current window.");
		}
	}

	public void scrollIntoView() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement detailsView = driver.findElement(By.id("address-details"));
		js.executeScript("arguments[0].scrollIntoView(true);", detailsView);
		System.out.println("====SCROLLED INTO VIEW====");
	}

	public void fillDetailsWithOneIncorrectValueInSender() {
		WebElement senderDetails = driver.findElement(By.id("sender-details"));
		senderDetails.findElement(By.id("firstname")).sendKeys("Ramu");
		senderDetails.findElement(By.id("lastname")).sendKeys("Singh");
		senderDetails.findElement(By.id("email")).sendKeys("RamuEmail");
		senderDetails.findElement(By.id("telephone")).sendKeys("9876543210");
	}

	public void fillDetailsWithOneIncorrectValueInReceiver() {
		WebElement receiverDetails = driver.findElement(By.id("receiver-details"));
		receiverDetails.findElement(By.id("firstname")).sendKeys("Ramu");
		receiverDetails.findElement(By.id("lastname")).sendKeys("Singh");
		receiverDetails.findElement(By.id("email")).sendKeys("RamuEmail");
		receiverDetails.findElement(By.id("giftMessage")).sendKeys("A gift for you");
	}

	public void takeScreenshot() {
		// Left as temporary local variable per request
		TakesScreenshot ss = (TakesScreenshot)driver;
		File file = ss.getScreenshotAs(OutputType.FILE);
		System.out.println("====SCREENSHOT TAKEN====");
	}

	public static void main(String args[]) {
		Scanner sc = new Scanner(System.in);
		GiftCards obj = new GiftCards();

		System.out.println("Enter the browser name (Chrome/Edge/Firefox): ");
		String driverInput = sc.nextLine();
		sc.close();

		try {
			obj.getDriverFromUser(driverInput);
			obj.handleShadowDOMAlert();
			obj.openGiftCardsPage();
			obj.changeWindowHandle();
			obj.scrollIntoView();
			obj.fillDetailsWithOneIncorrectValueInSender();
			obj.takeScreenshot();
			obj.fillDetailsWithOneIncorrectValueInReceiver();
			obj.takeScreenshot();

		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			// Optional: Close browser when done
			// if (driver != null) driver.quit();
		}
	}
}