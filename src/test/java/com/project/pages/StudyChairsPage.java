package com.project.pages;

import java.time.Duration;
import java.util.*;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class StudyChairsPage {
	private WebDriver driver;
	private WebDriverWait wait;

	 @FindBy(css = "ct-web-popup-imageonly")
	 private WebElement shadowHost;
	 
	@FindBy(xpath = "//div[@class='qJoGr']")
	private WebElement allFiltersBtn;
	
	@FindBy(xpath="//div[@class='thIUw' and @aria-label='Filter and Sort']")
	private WebElement filterDrawer;
	
	@FindBy(xpath = "//div[@role='button' and @aria-label='Sort']/span[@class='g9DqM']")
	private WebElement sortOption;

	@FindBy(xpath = "//div[@aria-label='Sort by Popularity']")
	private WebElement popularityOption;
	
	@FindBy(xpath = "//div[@class='H4_O_']")
	private WebElement closeFiltersTab;
	
	@FindBy(xpath = "//h2[@class='XxwSy']")
	private List<WebElement> studyChairs;

	private List<String> topThreeChairNames = new ArrayList<>();
	
	public StudyChairsPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		PageFactory.initElements(driver, this);
	}
	
	/** Sort results by Popularity*/
	public void sortByPopularity() {    
		wait.until(ExpectedConditions.elementToBeClickable(allFiltersBtn));
		allFiltersBtn.click(); 
		System.out.println("All Filters clicked");
		
		wait.until(ExpectedConditions.visibilityOf(filterDrawer));
		
		By sortBy = By.xpath("//div[@aria-label='Sort']");
		wait.until(ExpectedConditions.elementToBeClickable(sortBy)).click();
		//wait.until(ExpectedConditions.elementToBeClickable(sortOption));
		//System.out.println("Sort became clickable");
		//sortOption.click(); 
		/*wait.until(ExpectedConditions.visibilityOf(sortOption));
		System.out.println("Sort displayed: " + sortOption.isDisplayed());
		System.out.println("Sort enabled: " + sortOption.isEnabled());*/
		
		By popularityBy =
		        By.xpath("//div[@aria-label='Sort by Popularity']");

		wait.until(
		        ExpectedConditions.elementToBeClickable(popularityBy)
		).click();
		
		/*wait.until(ExpectedConditions.elementToBeClickable(popularityOption));
		popularityOption.click();*/
		
		By closeBtnBy =
		        By.xpath("//div[@class='H4_O_']");

		wait.until(
		        ExpectedConditions.elementToBeClickable(closeBtnBy)
		).click();
		wait.until(ExpectedConditions.visibilityOfAllElements(studyChairs));
		}
	
	/** Fetch top 3 study chair names    */
	public void fetchTopThreeStudyChairs() {
		topThreeChairNames.clear();
		int limit = Math.min(3, studyChairs.size());
		for (int i = 0; i < limit; i++) {
			String chairName = studyChairs.get(i).getText().trim();
			topThreeChairNames.add(chairName);           
			System.out.println("Study Chair " + (i + 1) + " : "+ chairName);
			}    
		}
	/*Returns the saved top 3 results*/
	public List<String> getTopThreeChairNames() {
		return topThreeChairNames;
		}
	
	 public void handlePopUpIfPresent() {
	        try {
	            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("ct-web-popup-imageonly")));
	            SearchContext shadowRoot = shadowHost.getShadowRoot();
	            WebElement closeButton = shadowRoot.findElement(By.cssSelector("#close"));
	            closeButton.click();
	            System.out.println("Pop-up successfully closed!");
	            Thread.sleep(1000);
	        } catch (Exception e) {
	            System.out.println("Pop-up skipped or not found.");
	        }
	    }
}
