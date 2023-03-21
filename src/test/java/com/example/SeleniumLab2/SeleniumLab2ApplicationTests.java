package com.example.SeleniumLab2;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SeleniumLab2ApplicationTests {

	private static WebDriver driver;

	@BeforeAll
	static void setupWebDriver() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--remote-allow-origins=*");
		driver = new ChromeDriver(options);
	}

	@BeforeEach
	void navigate() {
		driver.get("https://svtplay.se");

		WebElement acceptCookiesButton = driver.findElement(By.xpath("//*[@id=\"__next\"]/div[2]/div/div[2]/button[3]"));

		if (acceptCookiesButton.isDisplayed()) {
			acceptCookiesButton.click();
		}

	}

	@Test
	void checkWebsiteTitle() {
		String actualTitel = driver.getTitle();
		String expectedTitel = "SVT Play";
		assertEquals(expectedTitel, actualTitel, "Titel seems to be not correct.");
	}

	@Test
	void checkIfWebsiteLogoIsVisible() {
		WebElement logo = driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[3]/div/header/div[2]/div/div/nav/a"));
		Boolean isVisible = logo.isDisplayed();
		assertTrue(isVisible, "Logo seems to be not visible.");
	}

	@Test
	void checkNameOnStartLink() {
		WebElement startLink = driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[3]/div/header/div[2]/div/div/nav/ul/li[1]"));
		String actualText = startLink.getText();
		String expectedText = "START";
		assertEquals(expectedText, actualText, "Name on the start link seems to be not correct.");
	}

	@Test
	void checkNameOnProgramLink() {
		WebElement programLink = driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[3]/div/header/div[2]/div/div/nav/ul/li[2]/a"));
		String actualText = programLink.getText();
		String expectedText = "PROGRAM";
		assertEquals(expectedText, actualText,"Name on the program link seems to be not correct.");
	}

	@Test
	void checkNameOnChannelLink() {
		WebElement channelLink = driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[3]/div/header/div[2]/div/div/nav/ul/li[3]/a"));
		String actualText = channelLink.getText();
		String expectedText = "KANALER";
		assertEquals(expectedText, actualText, "Name on the channel link seems to be not correct.");
	}

	@Test
	void checkIfAvailabilityLinkIsVisible() {

		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		WebElement availabilityLink = driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[3]/div/footer/div/div[5]/div[2]/p[1]/a"));
		Boolean isVisible = availabilityLink.isDisplayed();
		assertTrue(isVisible, "Availability link seems to be not visible.");
	}

	@Test
	void checkTextOnAvailabilityLink() {
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		WebElement linkText = driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[3]/div/footer/div/div[5]/div[2]/p[1]/a/span[2]"));
		String actualText =	linkText.getText();
		String expectedText = "Tillgänglighet i SVT Play";
		assertEquals(expectedText, actualText, "The text seems to be not correct.");
	}

	@Test
	void checkHeaderTextOnAvailabilityPage() {
		WebElement hrefLink = driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[3]/div/footer/div/div[5]/div[2]/p[1]"));
		driver.get(hrefLink.findElement(By.tagName("a")).getAttribute("href"));
		WebElement availabilityHeader = driver.findElement(By.xpath("//*[@id=\"__next\"]/div[2]/main/div/div/div[1]/h1"));
		String actualHeaderText = availabilityHeader.getText();
		String expectedHeaderText = "Så arbetar SVT med tillgänglighet";
		assertEquals(expectedHeaderText, actualHeaderText, "Header text does not seem to be correct.");
	}

	@Test
	void checkNumberOfProgramCategories() {

		driver.manage().window().maximize();

		try {
			WebElement programLink = driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[3]/div/header/div[2]/div/div/nav/ul/li[2]/a"));
			programLink.click();
		} catch (Exception e) {
			WebElement programLink = driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[3]/div/header/div[2]/div/div/nav/ul/li[2]/a"));
			programLink.click();
		}

		WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait2.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".sc-a9073dc0-0.fVSyGp.sc-3b830fc0-4.dEXIAv")));
		List<WebElement> list = driver.findElements(By.cssSelector(".sc-a9073dc0-0.fVSyGp.sc-3b830fc0-4.dEXIAv"));
		assertEquals(18, list.size(), "The number of program categories does not seem to be correct.");

		setupWebDriver();

	}

	@Test
	void testNavigationToMainPagePopularCategory() {
		WebElement popularButtonDiv = driver.findElement(By.cssSelector("div.sc-f796aafd-0.bZntqJ"));
		driver.get(popularButtonDiv.findElement(By.tagName("a")).getAttribute("href"));
		WebElement pageHeader = driver.findElement(By.xpath("//*[@id=\"play_main-content\"]/section/h1"));
		String actualHeaderText = pageHeader.getText();
		String expectedHeaderText = "Populärt";
		assertEquals(expectedHeaderText, actualHeaderText, "Navigating to Populär does not seem to be working.");
	}

	@Test
	void testSearchBox() {

		driver.manage().window().maximize();

		try {
			new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(By.name("q"))).click();
		} catch (Exception e) {
			new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"search\"]"))).sendKeys("Rederiet");
			driver.findElement(By.xpath("//*[@id=\"combobox\"]/button/span[1]")).click();
		}

		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"play_main-content\"]/section/div/ul/li[1]/article/a/div[2]/h2/span/em")));

		WebElement programName = driver.findElement(By.xpath("//*[@id=\"play_main-content\"]/section/div/ul/li[1]/article/a/div[2]/h2/span/em"));
		String actualProgramName = programName.getText();
		String expectedProgramName = "Rederiet";
		assertEquals(expectedProgramName,actualProgramName, "The search box does not seem to function correctly.");

		setupWebDriver();

	}

	@Test
	void checkThatMainContentIsVisible() {
		WebElement main = driver.findElement(By.id("play_main-content"));
		Boolean result = main.isDisplayed();
		assertTrue(result, "Main content does not appear to be visible.");
	}

	@Test
	void checkContactUrl() {
		List<WebElement> contact = driver.findElements(By.className("sc-5b00349a-0"));
		String actualLinkText = contact.get(1).getAttribute("href");
		String expectedLinkText = "https://kontakt.svt.se/";
		assertEquals(expectedLinkText, actualLinkText, "Contact URL does not seem to be correct.");
	}

	@Test
	void checkTableauTabHeaderText() {
		driver.get("https://www.svtplay.se/kanaler");
		WebElement header = driver.findElement(By.xpath("//*[@id=\"play_main-content\"]/div/div[2]"));
		String actualfirstTabHeader = header.findElement(By.id("tab-0")).getText();
		String expectedFirstTabHeader = "Nu & snart";
		assertEquals(expectedFirstTabHeader, actualfirstTabHeader, "One or more header text does not seem to be correct.");
	}

	@AfterAll
	static void closeWebDriver() {
		driver.quit();
	}


}
