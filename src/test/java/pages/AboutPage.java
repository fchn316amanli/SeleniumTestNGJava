package pages;

import org.jspecify.annotations.Nullable;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.pagefactory.ByChained;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import testcases.BaseTest;


import java.sql.Array;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AboutPage extends BasePage {

    /*
    private WebDriver driver;
    private WebDriverWait wait;

    // Constructor accepts driver passed from the test script
    // Initialize driver and wait in the constructor
    public AboutPage(WebDriver driver) {
        this.driver = driver;
        //this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }
    */

    public AboutPage(WebDriver driver) {
        super(driver); // Chains the driver instance up to BasePage
    }

    //iFrame from https://www.bcldb.com/about/about-ldb
    public By cannabisVideoIFrame = By.name("cannabis-video");
    private By popupTitle = By.className("title-text");
    private By popupDescription = By.className("description-text");
    private By checkboxInput = By.cssSelector("input.checkbox-style[type='checkbox']");
    private By checkboxInputTxt = By.cssSelector(".checkbox-parent span");
    private By proceedBtn = By.xpath("//button[text()='Confirm & Proceed']");
    public By cannabisVideo = By.cssSelector("video.shaka-video");

    private By multiplyGoodLnk = By.xpath("//*[@id=\"block-bcldb-sidemenu-3\"]/ul/li[7]/a");

    //iFrame from https://www.bcldb.com/about/multiply-the-good
    public By youtubeVideoIFrame = By.cssSelector("p:nth-child(8) > iframe");
    // video play and pause through this div
    public By youtubeVideoDiv = By.cssSelector("div#player");
    // video and play/pause btn very unstable before playing
    // JavaScript to detect video status during video playing and pause
    public By youtubeVideo = By.cssSelector("video.video-stream.html5-main-video");


    // Page Actions
    public void moveToVideoIFrame(By byIframe){
        // Wait up to 30 seconds for the element to be present in the DOM
        WebElement videoIFrame = waitForElementToBeVisible(byIframe);
        //move to screen
        javaScriptExecutorScrollToViewTrue(videoIFrame);
    }

    public Map<String, String> getSrcTitleOfVideoIFrame(By byIframe){
        // Declare a map (Key: String, Value: String)
        Map<String, String> videoSrcTitle = new HashMap<>();

        WebElement videoIFrame = waitForElementToBeVisible(byIframe);
        //No need to switch to the frame driver,
        //because the src attribute exists directly on the <iframe> tag in the main HTML DOM
        String videoSrc = videoIFrame.getAttribute("src");
        String videoTitle = videoIFrame.getAttribute("title");

        System.out.println("videoSrc---" + videoSrc);
        System.out.println("videoTitle---" + videoTitle);

        videoSrcTitle.put("videoSrc", videoSrc);
        videoSrcTitle.put("videoTitle", videoTitle);
        return videoSrcTitle;
    }

    public Map<String, Double> getTimeStampsOfVideo(By byIframe, By byVideo) {
        // Declare a map (Key: String, Value: String)
        Map<String, Double> videoStatusData = new HashMap<>();
        // 1. Wait for iFrame visible
        WebElement videoIFrame = waitForElementToBeVisible(byIframe);
        // 2. Switch the driver's focus into the iFrame
        driver.switchTo().frame(videoIFrame);
        // 3. Locate the HTML5 <video> element inside the iFrame
        WebElement videoEl = waitForElementToBeClickable(byVideo);
        // 4. Initialize JavascriptExecutor
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        // 5. Let the video play for a moment, then grab the initial time
        threadSleep(10);
        double firstTimestamp = ((Number) executor
                .executeScript("return arguments[0].currentTime;", videoEl)).doubleValue();
        // 6. Wait a bit longer and check the time again
        threadSleep(5);
        double secondTimestamp = ((Number) executor
                .executeScript("return arguments[0].currentTime;", videoEl)).doubleValue();
        // 7. build map data
        videoStatusData.put("firstTimestamp", firstTimestamp);
        videoStatusData.put("secondTimestamp", secondTimestamp);
        // 8. Switch context back to the main webpage documentation
        driver.switchTo().defaultContent();
        // 9. return video status data
        return videoStatusData;
    }

    public boolean getPlayStatusOfVideo(By byIframe, By byVideo) {
        // 1. Wait for iFrame visible
        WebElement videoIFrame = (WebElement) waitForElementToBeVisible(byIframe);
        // 2. Switch the driver's focus into the iFrame
        driver.switchTo().frame(videoIFrame);
        // 3. Locate the HTML5 <video> element inside the iFrame
        WebElement videoEl = waitForElementToBeClickable(byVideo);
        // 4. Initialize JavascriptExecutor
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        // 5. Get video status
        boolean isPaused = (Boolean) executor
                .executeScript("return arguments[0].paused;", videoEl);
        // 6. Switch context back to the main webpage documentation
        driver.switchTo().defaultContent();
        // 7. return video status
        return isPaused;
    }

    public void playPauseVideo(By byIframe, By byVideo) {
        WebElement videoIFrame = (WebElement) waitForElementToBeVisible(byIframe);
        // 1. Switch to the iFrame
        driver.switchTo().frame(videoIFrame);
        // 2. Instantiate the Actions class by passing the WebDriver instance
        Actions actions = new Actions(driver);
        // 3. get the UI el, handle a mouse over (hover) and click action
        WebElement videoEl = waitForElementToBeClickable(byVideo);
        actions.moveToElement(videoEl).click().perform();
        // 4. Switch back the driver from iFrame to page
        driver.switchTo().defaultContent();
    }

    public Map<String, String> getPopupBoxTextsOfCannabisVideo(){
        // Declare a map (Key: String, Value: String)
        Map<String, String> popupBoxTexts = new HashMap<>();

        WebElement videoIFrame = waitForElementToBeVisible(cannabisVideoIFrame);
        //Switch to the frame
        driver.switchTo().frame(videoIFrame);
        String titleTxt = waitForElementToBeVisible(popupTitle).getText();
        String descriptionTxt = waitForElementToBeVisible(popupDescription).getText();
        String checkBoxTxt = waitForElementToBeVisible(checkboxInputTxt).getText();
        String proceedBtnTxt = waitForElementToBeVisible(proceedBtn).getText();

        System.out.println("titleTxt---" + titleTxt);
        System.out.println("descriptionTxt---" + descriptionTxt);
        System.out.println("checkBoxTxt---" + checkBoxTxt);
        System.out.println("proceedBtnTxt---" + proceedBtnTxt);

        popupBoxTexts.put("titleTxt", titleTxt);
        popupBoxTexts.put("descriptionTxt", descriptionTxt);
        popupBoxTexts.put("checkBoxTxt", checkBoxTxt);
        popupBoxTexts.put("proceedBtnTxt", proceedBtnTxt);
        //leave frame
        driver.switchTo().defaultContent();
        return popupBoxTexts;
    }

    public void confirmProceedCannabisVideo() {
        WebElement videoIFrame = waitForElementToBeVisible(cannabisVideoIFrame);
        //Switch to the frame
        driver.switchTo().frame(videoIFrame);
        WebElement checkbox = waitForElementToBeClickable(checkboxInput);
        // before select the checkbox, this el is NOT clickable
        WebElement confirmProceedBtn = waitForElementToBeVisible(proceedBtn);
        if (!checkbox.isSelected()) {
            checkbox.click();
            confirmProceedBtn.click();
        } else {
            confirmProceedBtn.click();
        }
        //leave frame
        driver.switchTo().defaultContent();
    }

    public void selectMultiplyTheGoodLink(){
        WebElement el = waitForElementToBeClickable(multiplyGoodLnk);
        //move to screen
        javaScriptExecutorScrollToViewTrue(el);
        el.click();
    }


}
