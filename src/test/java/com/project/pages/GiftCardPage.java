package com.project.pages;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.project.Utils.ScreenshotUtil;

public class GiftCardPage {

	private WebDriver driver;
	private WebDriverWait wait;
	private static final Logger log =LogManager.getLogger(GiftCardPage.class);

	// CARD
	@FindBy(xpath = "//img[contains(@class,'img-fluid')]")
	private WebElement anniversaryCard;

	// AMOUNT & QUANTITY
	@FindBy(id = "denomination")
	private WebElement amountInput;

	@FindBy(id = "quantity")
	private WebElement quantityInput;

	//scrolltargets
	@FindBy(xpath = "//div[@class='justify-content-center row']")
	private WebElement scrollTargetCards;


	// SENDER DETAILS
	@FindBy(xpath = "//div[@id='sender-details']//input[@id='firstname']")
	private WebElement senderFirstName;

	@FindBy(xpath = "//div[@id='sender-details']//input[@id='lastname']")
	private WebElement senderLastName;

	@FindBy(css = "#sender-details #email")
	private WebElement senderEmail;

	@FindBy(id = "telephone")
	private WebElement senderPhone;

	// RECEIVER DETAILS
	@FindBy(xpath = "//div[@id='receiver-details']//input[@id='firstname']")
	private WebElement receiverFirstName;

	@FindBy(xpath = "//div[@id='receiver-details']//input[@id='lastname']")
	private WebElement receiverLastName;

	@FindBy(css = "#receiver-details #email")
	private WebElement receiverEmail;

	// PAY NOW
	@FindBy(id = "pay-now-button")
	private WebElement payNowButton;

	// ERROR MESSAGE
	@FindBy(css = ".invalid-address")
	private WebElement errorMessage;

	public GiftCardPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		PageFactory.initElements(driver, this);
	}

	public void navigateToGiftCardPage() {
		//driver.get(                "https://urbanladder.woohoo.in/en-gb/digital/urban-ladder-e-gift-card");
		driver.navigate().to(
				"https://urbanladder.woohoo.in/en-gb/digital/urban-ladder-e-gift-card"
				);
		log.info("Navigated to Gift Card Page");
	}

	public void enterAmount(String amount) {
		// to avoid stale element exception
		wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.id("denomination")));
		amountInput.clear();
		amountInput.sendKeys(amount);
		log.info("Amount entered: {}", amount);
	}

	public void enterQuantity(String quantity) {
		quantityInput.clear();
		quantityInput.sendKeys(quantity);
		log.info("Quantity entered: {}", quantity);
	}

	public void selectAnniversaryCard() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript(
				"arguments[0].scrollIntoView({block:'center'});",
				scrollTargetCards
				);

		wait.until(
				ExpectedConditions.elementToBeClickable(anniversaryCard));

		((JavascriptExecutor) driver).executeScript("arguments[0].click();", anniversaryCard);
		log.info("Anniversary card selected");
	}


	public void scrollToForm() {

		WebElement formSection =
				wait.until(
						ExpectedConditions.presenceOfElementLocated(
								By.xpath("//div[@class='justify-content-center mb-2 row']")));
		((JavascriptExecutor) driver)
		.executeScript(
				"arguments[0].scrollIntoView({block:'center'});",
				formSection);

		log.info("Scrolled to Gift Card Form");

	}

	public void fillGiftCardDetails() {
		senderFirstName.sendKeys("Tester");
		senderLastName.sendKeys("User");
		senderEmail.sendKeys("tester@gmail.com");
		senderPhone.sendKeys("9876543210");
		receiverFirstName.sendKeys("James");
		receiverLastName.sendKeys("Bond");

		// INVALID EMAIL
		receiverEmail.sendKeys("aehjbfkse");

		//receiverEmail.sendKeys(Keys.TAB);
		log.info("Sender and Receiver details entered");
	}

	public void scrollToPayButton() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript(
				"arguments[0].scrollIntoView({block:'center'});",
				payNowButton
				);
		log.info("Scrolled to Pay Now button");
	}

	public void clickPayNow() {
		wait.until(
				ExpectedConditions.visibilityOf(payNowButton));

		((JavascriptExecutor) driver)
		.executeScript("arguments[0].click();", payNowButton);
		log.info("Pay Now clicked");
	}

	public void takeScreenshot(String imageName) {
		try {
			ScreenshotUtil.capture(driver, imageName);
			log.info("Screenshot successfully stored: " + imageName + ".png");
		} catch (Exception e) {
			log.error("Failed to capture picture: " + e.getMessage());
		}
	}

	public String captureErrorMessage() {
		wait.until(
				ExpectedConditions.visibilityOf(errorMessage));

		String msg = errorMessage.getText().trim();
		log.info("Captured Error Message: {}", msg);
		return msg;
	}
}
