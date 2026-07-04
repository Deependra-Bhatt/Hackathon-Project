package com.project.collections;

import java.time.Duration;
import java.util.Scanner;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions; // Imported for hovering
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Collections {
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
	
	public void openStudyChairsPageByNabar() {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    Actions actions = new Actions(driver);
    
    // 1. Fixed XPath: Target the div directly using its unique aria-label attribute
    WebElement studyHeader = wait.until(
        ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//div[@role='button' and @aria-label='Study menu']")
        )
    );
    
    // 2. Hover over 'Study' to display the dropdown submenu
    actions.moveToElement(studyHeader).perform();
    System.out.println("Hovered over Study heading.");
    
    // 3. Wait for the Study Chair sub-link to appear and click it
    WebElement bookshelveLink = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath("//a[@class='MBplo' and contains(@href, '/collection/study-chairs')]")
        )
    );
    bookshelveLink.click();
    System.out.println("Successfully clicked Study Chair link.");
}
	
	public void applySortByPopularityFilter() {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    
	    // 1. Target the actual interactive button element
	    WebElement sortByButton = wait.until(
	        ExpectedConditions.elementToBeClickable(
	            By.xpath("//div[@role='button' and contains(@aria-label, 'Sort By filter')]")
	        )
	    );
	    
	    // 2. Click to open the dropdown
	    sortByButton.click(); 
	    
	    // 3. Wait for the dropdown menu to expand (aria-expanded turns true)
	    wait.until(ExpectedConditions.attributeToBe(sortByButton, "aria-expanded", "true"));
	    
	    // 4. Locate and click your "popularity" option
	    WebElement openStorageOption = wait.until(
	        ExpectedConditions.elementToBeClickable(
	            By.xpath("//div[@id='dropdown-menu-sort-by']//*[contains(text(), 'Popularity') or @value='Popularity']")
	        )
	    );
	    openStorageOption.click();
	    System.out.println("Sort By filter applied successfully!");
	}

	public void displayTopThreeStudyChair() {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    
	    // 1. Wait for the product cards to be present on the page
	    // Using the common class for the parent link elements ('xmdLL')
	    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@role='link' and contains(@class, 'xmdLL')]")));
	    
	    // 2. Fetch all matching product card containers
	    List<WebElement> chairsCards = driver.findElements(By.xpath("//div[@role='link' and contains(@class, 'xmdLL')]"));
	    
	    System.out.println("\n--- Top 3 Study Chairs By Popularity ---");
	    
	    for (int i=0;i<3;i++) {
	       
	    	WebElement card = chairsCards.get(i);
	        try {
	            // 3. Extract the Name (from the h2 tag)
	            String name = card.findElement(By.xpath(".//h2[@class='XxwSy']")).getText().trim();
	            
	            // 4. Extract the Deal Price (using the text within the UYQNp div)
	            String priceText = card.findElement(By.xpath(".//div[@class='UYQNp']")).getText().trim();
	            
	            // Clean the price string (remove '₹' and ',' commas) to convert it to a number
	            // e.g., "₹7,999" becomes "7999"
//	            String cleanPrice = priceText.replaceAll("[^0-9]", "");
//	            int price = Integer.parseInt(cleanPrice);
	            
	            // 5. Check if the price is below 15000
	           
	                System.out.println((i+1)+ ". Name: " + name);
	                System.out.println("   Price: " + priceText);
	                System.out.println("------------------------------------------------");
	            
	        } catch (Exception e) {
	            // Catching exceptions for safe iteration in case a specific element fails to load
	            continue;
	        }
	    }
	    
	    if (chairsCards.isEmpty()) {
	        System.out.println("No bookshelves found below Rs. 15,000.");
	    }
	}
	
	public static void main(String args[]) {
		Scanner sc = new Scanner(System.in);
		Collections obj = new Collections();
		
		System.out.println("Enter the browser name (Chrome/Edge/Firefox): ");
		String driverInput = sc.nextLine();
		sc.close();
		
		try {
			obj.getDriverFromUser(driverInput);
			obj.handleShadowDOMAlert();
			obj.openStudyChairsPageByNabar();
			obj.applySortByPopularityFilter();
			obj.handleShadowDOMAlert();
			obj.displayTopThreeStudyChair();
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			// Optional: Close browser when done
//			 if (driver != null) driver.quit();
		}
	}
}