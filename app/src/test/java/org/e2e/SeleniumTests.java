package org.e2e;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class SeleniumTests {
    private WebDriver driver;
    static private ChromeOptions options;
    static public int waitTime = 10;
    static public String newsDropdownXpath = "/html/body/div[1]/div/div/header/nav/div/nav/ul/li[3]/a";
    static public String dropdownMenuXpath = "/html/body/div[1]/div/div/header/nav/div/nav/ul/li[3]/ul";
    static public String newsroomXpath = "/html/body/div[1]/div/div/header/nav/div/nav/ul/li[3]/ul/li[1]/a";
    static public String newsContainerId = "block-views-block-hdp-news-room-block-1";

    @BeforeAll
    public static void browsersetup() {
        options = new ChromeOptions();
        System.setProperty("webdriver.chrome.verboseLogging", "true");
    }

    @BeforeEach
    public void setup() {
        WebDriverManager.chromedriver().browserVersion("130.0.6723.59").setup();
        driver = new ChromeDriver(options);
    }

    @AfterEach
    public void close() {
        if (driver != null) {
            driver.close();
        }
    }

    @Test
    public void accessPage() {
        driver.get("https://corporate.homedepot.com/");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitTime));

        WebElement newsDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(newsDropdownXpath)));
        newsDropdown.click();

        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement dropdownMenu = driver.findElement(By.xpath(dropdownMenuXpath));
        js.executeScript("arguments[0].classList.add('show');", dropdownMenu);


        WebElement newsroomOption = driver.findElement(By.xpath(newsroomXpath));
        newsroomOption.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(newsContainerId)));
        Assertions.assertEquals("Newsroom | The Home Depot", driver.getTitle());
    }
}