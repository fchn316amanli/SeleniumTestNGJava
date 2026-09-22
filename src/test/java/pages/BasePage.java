package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    // Constructor accepts driver passed from the test script
    // Initialize driver and wait in the constructor
    public BasePage(WebDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("Driver must be set and cannot be null!");
        }
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    protected void threadSleep(int seconds){
        try {
            System.out.println(seconds + " seconds wait!");
            Thread.sleep(Duration.ofSeconds(seconds));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // Centrally managed explicit wait mechanism
    protected WebElement waitForElementToBeVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // Centrally managed explicit wait mechanism
    protected WebElement waitForElementToBeClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected void javaScriptExecutorScrollToViewTrue(WebElement el){
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        //move to screen
        executor.executeScript("arguments[0].scrollIntoView(true);",el );
    }

    protected void javaScriptExecutorScrollToViewCenterAndClick(WebElement el){
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        //move to screen
        executor.executeScript("arguments[0].scrollIntoView({block: 'center'});",el );
        // Bypasses Selenium's cursor restrictions and forces a click
        executor.executeScript("arguments[0].click();", el );
    }

}
